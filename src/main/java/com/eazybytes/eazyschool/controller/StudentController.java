package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.PersonCourse;
import com.eazybytes.eazyschool.repository.PersonCourseRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {
    private final PersonCourseRepository personCourseRepository;

    public StudentController(PersonCourseRepository personCourseRepository) {
        this.personCourseRepository = personCourseRepository;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession session)
    {
        Person person = (Person) session.getAttribute("loggedInPerson");
        List<PersonCourse> personCourses = personCourseRepository.findByPersonPersonId(person.getPersonId());
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person",person);
        modelAndView.addObject("personCourses",personCourses);
        return modelAndView;
    }
}
