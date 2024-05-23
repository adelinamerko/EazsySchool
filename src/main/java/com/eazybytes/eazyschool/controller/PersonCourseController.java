package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.service.PersonCourseService;
import com.eazybytes.eazyschool.utils.RequestType;
import com.eazybytes.eazyschool.utils.Status;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class PersonCourseController {
    PersonCourseService personCourseService;

    @Autowired
    public PersonCourseController(PersonCourseService personCourseService) {
        this.personCourseService = personCourseService;
    }

    @GetMapping("/student/unregister")
    public String unregisterCourse(@RequestParam int courseId, HttpSession session) {
        log.error("Unregistering course with ID: " + courseId);
        Person loggedInPerson = (Person) session.getAttribute("loggedInPerson");
        personCourseService.updateStatusAndRequestType(courseId, loggedInPerson.getPersonId(), RequestType.UNREGISTER, Status.PENDING_UNREGISTRATION);
        return "redirect:/courses";
    }

    @GetMapping("/student/register")
    public String registerCourse(@RequestParam int courseId, HttpSession session) {
        log.error("Registering course with ID: " + courseId);
        Person loggedInPerson = (Person) session.getAttribute("loggedInPerson");
        personCourseService.updateStatusAndRequestType(courseId, loggedInPerson.getPersonId(), RequestType.REGISTER, Status.PENDING_REGISTRATION);
        return "redirect:/courses";
    }


}
