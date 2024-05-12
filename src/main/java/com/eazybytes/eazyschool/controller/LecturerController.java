package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("lecturer")
public class LecturerController {
    private final CoursesRepository coursesRepository;
    private final PersonRepository personRepository;

    @Autowired
    public LecturerController(PersonRepository personRepository, CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
        this.personRepository = personRepository;
    }
    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        List<Courses> courses = coursesRepository.findAllCoursesForPerson(person.getPersonId());
        System.out.println("Courses: "+courses);
        ModelAndView modelAndView = new ModelAndView("courses_lecturer.html");
        modelAndView.addObject("courses",courses);
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
}
