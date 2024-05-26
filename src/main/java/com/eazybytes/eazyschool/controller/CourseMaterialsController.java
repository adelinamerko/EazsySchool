package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.service.CourseMaterialService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("courseMaterials")
public class CourseMaterialsController {
    private final CoursesRepository coursesRepository;
    private final CourseMaterialService courseMaterialService;

    @Autowired
    public CourseMaterialsController(CoursesRepository coursesRepository, CourseMaterialService courseMaterialService) {
        this.coursesRepository = coursesRepository;
        this.courseMaterialService = courseMaterialService;
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
    public String addMaterialToCourse(@RequestParam("courseId") int courseId, @RequestParam("file") MultipartFile file) {
        Optional<Courses> optionalCourse = coursesRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Courses course = optionalCourse.get();
            try {
                courseMaterialService.addCourseMaterial(course.getCourseId(), file);
            } catch (IOException e) {
                log.error("Error occurred while uploading the file: {}", e.getMessage());
            }
        }
        return "redirect:/courseMaterials/viewMaterials?id=" + courseId;
    }

    @GetMapping("/deleteMaterialFromCourse")
    public String deleteMaterialFromCourse(@RequestParam("materialId") int materialId, HttpSession session) {
        try {
            courseMaterialService.deleteMaterial(materialId);
        } catch (IOException e) {
            log.error("Error occurred while deleting the file: {}", e.getMessage());
            return "redirect:/courseMaterials/viewMaterials?id=" + ((Courses) session.getAttribute("course")).getCourseId() + "&error=file-delete-error";
        }
        return "redirect:/courseMaterials/viewMaterials?id=" + ((Courses) session.getAttribute("course")).getCourseId();
    }
}
