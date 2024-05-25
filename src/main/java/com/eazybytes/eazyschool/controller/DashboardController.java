package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.PersonCourse;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.PersonCourseRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import com.eazybytes.eazyschool.utils.RequestType;
import com.eazybytes.eazyschool.utils.Status;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @Autowired
    PersonCourseRepository personCourseRepository;

    @Value("${eazyschool.pageSize}")
    private int defaultPageSize;

    @Value("${eazyschool.contact.successMsg}")
    private String message;

    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model,Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if(null != person.getEazyClass() && null != person.getEazyClass().getName()){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }
        session.setAttribute("loggedInPerson", person);
        logMessages();
        return "dashboard.html";
    }

    @GetMapping("/courses")
    public ModelAndView getCourses() {
        ModelAndView modelAndView = new ModelAndView("courses.html");
        List<Courses> courses = coursesRepository.findAll();
        Map<Integer, Integer> courseRates = new HashMap<>(); // Map to store course ID and rate

        for (Courses course : courses) {
            log.error("Course Name : " + course.getName());
            log.error("Course Fees : " + course.getFees());
            log.error("Course Description : " + course.getDescription());
            log.error("Course Image Path : " + course.getCourseImagePath());

            Integer rate = personCourseRepository.findCourseRate(course.getCourseId());
            courseRates.put(course.getCourseId(), rate);
        }

        modelAndView.addObject("courses", courses);
        modelAndView.addObject("courseRates", courseRates);
        return modelAndView;
    }

    @GetMapping("/courses/{courseId}")
    public ModelAndView getCourseById(@PathVariable Integer courseId, HttpSession session) {
        boolean isStudentRegistered = false;
        boolean hasStudentRated = false;
        boolean canStudentUnregister = false;
        boolean canStudentRegister = true;

        Person person = (Person) session.getAttribute("loggedInPerson");
        PersonCourse personCourse = personCourseRepository.findByPersonPersonIdAndCourseCourseId(person.getPersonId(), courseId);
        Integer rating = personCourseRepository.findCourseRate(courseId);

        if (personCourse != null) {
            isStudentRegistered = personCourse.getStatus() == Status.REGISTERED || personCourse.getStatus() == Status.PENDING_UNREGISTRATION;
            hasStudentRated = personCourse.getRating() > 0;
            canStudentUnregister = (personCourse.getStatus() == Status.REGISTERED && personCourse.getRequestType() != RequestType.UNREGISTER) ;
            canStudentRegister = (personCourse.getStatus() == null ||  personCourse.getStatus() == Status.UNREGISTERED) && personCourse.getRequestType() != RequestType.REGISTER;
        }
        ModelAndView modelAndView = new ModelAndView("course_details.html");
        modelAndView.addObject("isStudentRegistered", isStudentRegistered);
        modelAndView.addObject("hasStudentRated", hasStudentRated);
        modelAndView.addObject("canStudentUnregister", canStudentUnregister);
        modelAndView.addObject("canStudentRegister", canStudentRegister);

        Courses course = coursesRepository.findByCourseId(courseId);
        if (course == null) {
            modelAndView.addObject("errorMessage", "Course not found");
            return modelAndView;
        }
        modelAndView.addObject("course", course);
        if (rating != null) {
            modelAndView.addObject("rating", rating);
        } else {
            modelAndView.addObject("rating", 0);
        }
        return modelAndView;
    }

    @PostMapping("/courses/{courseId}")
    public String updateCourseRating(@PathVariable int courseId, @RequestParam("rating") int rating, HttpSession session) {
        Person loggedInPerson = (Person) session.getAttribute("loggedInPerson");
        // Update the rating for the course and the logged-in person
        log.info("Updating rating for personId : " + loggedInPerson.getPersonId() + " and courseId : " + courseId + " with rating : " + rating);
        PersonCourse personCourse = personCourseRepository.findByPersonPersonIdAndCourseCourseId(loggedInPerson.getPersonId(), courseId);
        if (personCourse != null) {
            personCourse.setRating(rating);
            personCourseRepository.save(personCourse);
        } else {
            log.error("PersonCourse not found for personId : " + loggedInPerson.getPersonId() + " and courseId : " + courseId);
        }
        return "redirect:/courses/" + courseId;
    }

    private void logMessages() {
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");

        log.error("defaultPageSize value with @Value annotation is : "+defaultPageSize);
        log.error("successMsg value with @Value annotation is : "+message);

        log.error("defaultPageSize value with Environment is : "+environment.getProperty("eazyschool.pageSize"));
        log.error("successMsg value with Environment is : "+environment.getProperty("eazyschool.contact.successMsg"));
        log.error("Java Home environment variable using Environment is : "+environment.getProperty("JAVA_HOME"));
    }

}
