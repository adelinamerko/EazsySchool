package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import com.eazybytes.eazyschool.service.CourseMaterialService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("lecturer")
public class LecturerController {
    private final CoursesRepository coursesRepository;
    private final PersonRepository personRepository;
    private final CourseMaterialService courseMaterialService;

    @Autowired
    public LecturerController(PersonRepository personRepository, CoursesRepository coursesRepository, CourseMaterialService courseMaterialService) {
        this.coursesRepository = coursesRepository;
        this.personRepository = personRepository;
        this.courseMaterialService = courseMaterialService;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        List<Courses> courses = coursesRepository.findAllCoursesForPerson(person.getPersonId());
        ModelAndView modelAndView = new ModelAndView("courses_lecturer.html");
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students_lecturer.html");
        List<Person> students = personRepository.findByCourseIdAndRoleName(id, "STUDENT");
        Courses course = coursesRepository.findById(id).orElse(null);
        modelAndView.addObject("course", course);
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @GetMapping("/viewMaterials")
    public ModelAndView viewMaterials(@RequestParam("id") int courseId, HttpSession session,
                                      @RequestParam(name = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("course_materials.html"); // Assuming "course_materials" is the Thymeleaf template name
        Optional<Courses> coursesOptional = coursesRepository.findById(courseId);
        if (coursesOptional.isPresent()) {
            Courses course = coursesOptional.get();
            modelAndView.addObject("course", course);
            session.setAttribute("course", course);
            if (error != null) {
                modelAndView.addObject("errorMessage", "Invalid email entered!!");
            }
        } else {
            modelAndView.addObject("errorMessage", "Course not found!");
        }
        return modelAndView;
    }

    @PostMapping("/addMaterialToCourse")
    public String addMaterialToCourse(@RequestParam("courseId") int courseId,
                                      @RequestParam("file") MultipartFile file,
                                      HttpSession session) {
        Optional<Courses> optionalCourse = coursesRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Courses course = optionalCourse.get();
            try {
                courseMaterialService.addCourseMaterial(course.getCourseId(), file);
            } catch (IOException e) {
                // Handle file upload exception
                e.printStackTrace();
                // Redirect to the viewMaterials page with an error message
            }
        }
        // Redirect to the viewMaterials page
        return "redirect:/lecturer/viewMaterials?id=" + courseId;
    }

    @GetMapping("/deleteMaterialFromCourse")
    public String deleteMaterialFromCourse(@RequestParam("materialId") int materialId,
                                           HttpSession session) {
        try {
            courseMaterialService.deleteMaterial(materialId);
        } catch (IOException e) {
            // Handle file deletion exception
            e.printStackTrace();
            return "redirect:/lecturer/viewMaterials?id=" + ((Courses) session.getAttribute("course")).getCourseId() + "&error=file-delete-error";

        }
        return "redirect:/lecturer/viewMaterials?id=" + ((Courses) session.getAttribute("course")).getCourseId();
    }

}
