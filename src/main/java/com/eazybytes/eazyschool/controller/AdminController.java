package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.*;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.EazyClassRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import com.eazybytes.eazyschool.service.PersonService;
import com.eazybytes.eazyschool.utils.Utilities;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    EazyClassRepository eazyClassRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @Autowired
    PersonService personService;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<EazyClass> eazyClasses = eazyClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("eazyClasses", eazyClasses);
        modelAndView.addObject("eazyClass", new EazyClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("eazyClass") EazyClass eazyClass) {
        eazyClassRepository.save(eazyClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        for (Person person : eazyClass.get().getPersons()) {
            person.setEazyClass(null);
            personRepository.save(person);
        }
        eazyClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(classId);
        modelAndView.addObject("eazyClass", eazyClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("eazyClass", eazyClass.get());
        if (error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId()
                                     + "&error=true");
            return modelAndView;
        }
        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        eazyClassRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setEazyClass(null);
        eazyClass.getPersons().remove(person.get());
        EazyClass eazyClassSaved = eazyClassRepository.save(eazyClass);
        session.setAttribute("eazyClass", eazyClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
        //List<Courses> courses = coursesRepository.findByOrderByNameDesc();
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").descending());
        List<Person> lecturers = personRepository.findByRolesRoleName(Sort.by("name").descending(), "LECTURER");
        for (Courses course : courses) {
            log.error("Course: " + course.getName());
        }
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());
        modelAndView.addObject("lecturers", lecturers);
        for (Person lecturer : lecturers) {
            log.error("Lecturer: " + lecturer.getName());
        }
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute("course") Courses course, @RequestParam("lecturer") int lecturerId, @RequestParam(value = "courseImage", required = false) MultipartFile courseImageFile) {
        ModelAndView modelAndView = new ModelAndView();
        // Fetch the lecturer by ID
        Person lecturer = personRepository.findById(lecturerId).orElse(null);
        if (lecturer != null) {
            // Set the lecturer for the course
            course.getPersons().add(lecturer);
            // Add the course to the lecturer's set of courses
            lecturer.getCourses().add(course);
            if (courseImageFile != null && !courseImageFile.isEmpty()) {
                String courseImagePath = Utilities.uploadCourseImage(courseImageFile, course);
                course.setCourseImagePath(courseImagePath);
            }
            // Save both the course and the lecturer
            coursesRepository.save(course);
            personRepository.save(lecturer);
        }
        // Redirect to the displayCourses page
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }


    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id
            , HttpSession session, @RequestParam(required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses", courses.get());
        if (error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person,
                                           HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId()
                                     + "&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId,
                                                HttpSession session) {
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses", courses);
        ModelAndView modelAndView = new
                ModelAndView("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @RequestMapping(value = "/addLecturer", method = {RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register.html";
    }

    @PostMapping("/createLecturer")
    public String createLecturer(@Valid @ModelAttribute("person") Person person, Errors errors,
                             @RequestParam("profileImageFile") MultipartFile file,
                             @RequestParam(value = "role", defaultValue = EazySchoolConstants.STUDENT_ROLE) String role) {
        if (errors.hasErrors()) {
            return "register.html";
        }

        if (!file.isEmpty()) {
            String profileImagePath = Utilities.uploadProfileImage(file, person);
            person.setProfileImagePath(profileImagePath);
        }

        boolean isSaved = personService.createNewPerson(person, role);

        if (isSaved) {
            return "redirect:/dashboard";
        } else {
            return "register.html";
        }
    }


}
