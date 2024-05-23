package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.*;
import com.eazybytes.eazyschool.repository.*;
import com.eazybytes.eazyschool.service.*;
import com.eazybytes.eazyschool.utils.*;
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

    @Autowired
    PersonCourseRepository personCourseRepository;

    @Autowired
    PersonCourseService personCourseService;

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
/*        for (Courses course : courses) {
            log.error("Course: " + course.getName());
            for (PersonCourse personCourse : course.getPersonCourses()) {
                log.error("Person: " + personCourse.getPerson().getName());
            }
        }*/
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
            // Handle course image upload
            if (courseImageFile != null && !courseImageFile.isEmpty()) {
                String courseImagePath = Utilities.uploadCourseImage(courseImageFile, course);
                course.setCourseImagePath(courseImagePath);
            }

            // Save the course
            coursesRepository.save(course);

            // Create and save the PersonCourse entity to establish the relationship
            PersonCourse personCourse = new PersonCourse(new PersonCourseId(lecturer, course));

            personCourseRepository.save(personCourse);
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
        // Check if the student is already enrolled in the course
        PersonCourse existingPersonCourse = personCourseRepository.findById(new PersonCourseId(personEntity, courses)).orElse(null);
        if (personEntity == null || !(personEntity.getPersonId() > 0) || existingPersonCourse != null) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId()
                                     + "&error=true");
            return modelAndView;
        }
        // Create and save the PersonCourse entity to establish the relationship
        PersonCourse personCourse = new PersonCourse(new PersonCourseId(personEntity, courses), Status.REGISTERED);

        personCourseRepository.save(personCourse);

        session.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person person = personRepository.findById(personId).orElse(null);

        if (person != null && courses != null) {
            // Find the PersonCourse entry
            PersonCourseId personCourseId = new PersonCourseId(person, courses);
            Optional<PersonCourse> personCourse = personCourseRepository.findById(personCourseId);

            // Delete the PersonCourse entry to remove the relationship
            personCourse.ifPresent(course -> personCourseRepository.delete(course));
        }

        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @RequestMapping(value = "/addLecturer", method = {RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register.html";
    }

    @GetMapping("/displayRequests")
    public ModelAndView displayRequests() {
        ModelAndView modelAndView = new ModelAndView("requests.html");
        List<PersonCourse> courseRequests = personCourseService.getAllCourseRequests();
        modelAndView.addObject("courseRequests", courseRequests);
        return modelAndView;
    }

    @GetMapping("/approveRegistration")
    public String approveRegistration(@RequestParam("personId") int personId,
                                      @RequestParam("courseId") int courseId) {
        personCourseService.updateStatusAndRequestType(courseId, personId, RequestType.NONE, Status.REGISTERED);
        return "redirect:/admin/displayRequests";
    }

    @GetMapping("/approveUnregistration")
    public String approveUnregistration(@RequestParam("personId") int personId,
                                        @RequestParam("courseId") int courseId) {
        personCourseService.updateStatusAndRequestType(courseId, personId, RequestType.NONE, Status.UNREGISTERED);
        return "redirect:/admin/displayRequests";
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
