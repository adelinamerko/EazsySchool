package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.PersonCourse;
import com.eazybytes.eazyschool.model.PersonCourseId;
import com.eazybytes.eazyschool.repository.CoursesRepository;
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
    CoursesRepository coursesRepository;

    @Autowired
    public PersonCourseController(PersonCourseService personCourseService, CoursesRepository coursesRepository) {
        this.personCourseService = personCourseService;
        this.coursesRepository = coursesRepository;
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
        Person loggedInPerson = (Person) session.getAttribute("loggedInPerson");
        PersonCourse personCourse = personCourseService.findByPersonIdAndCourseId(loggedInPerson.getPersonId(), courseId);
        Courses course = coursesRepository.findById(courseId).get();
        if (personCourse == null) {
            //register to the course
            PersonCourse personCourseToAdd = new PersonCourse(new PersonCourseId(loggedInPerson, course), RequestType.REGISTER, Status.PENDING_REGISTRATION);
            personCourseService.savePersonCourse(personCourseToAdd);
        } else {
            //update the course status
            personCourseService.updateStatusAndRequestType(courseId, loggedInPerson.getPersonId(), RequestType.REGISTER, Status.PENDING_REGISTRATION);
        }
        return "redirect:/courses";
    }


}
