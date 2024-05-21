package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

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
        for (Courses course : courses) {
            log.error("Course Name : " + course.getName());
            log.error("Course Fees : " + course.getFees());
            log.error("Course Description : " + course.getDescription());
            log.error("Course Image Path : " + course.getCourseImagePath());
        }
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    @GetMapping("/courses/{courseId}")
    public ModelAndView getCourseById(@PathVariable Integer courseId, HttpSession session) {
        boolean isStudentRegistered = false;
        Person person = (Person) session.getAttribute("loggedInPerson");
        Courses personCourse = coursesRepository.findCourseForPerson(person.getPersonId(), courseId);
        if (personCourse != null) {
            isStudentRegistered = true;
        }
        ModelAndView modelAndView = new ModelAndView("course_details.html");
        modelAndView.addObject("isStudentRegistered", isStudentRegistered);
        Courses course = coursesRepository.findByCourseId(courseId);
        if (course == null) {
            modelAndView.addObject("errorMessage", "Course not found");
            return modelAndView;
        }
        modelAndView.addObject("course", course);
        log.error("Course Name : " + course.getName());
        log.error("Course Fees : " + course.getFees());
        log.error("Course Description : " + course.getDescription());
        log.error("Course Image Path : " + course.getCourseImagePath());
        log.error("IsStudentRegistered : " + isStudentRegistered);
        return modelAndView;
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
