package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.service.PersonService;
import com.eazybytes.eazyschool.utils.Utilities;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("public")
public class PublicController {
    // Define the folder where uploaded images will be stored

    @Autowired
    PersonService personService;
    @Autowired
    private HttpSession httpSession;

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register.html";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors errors,
                             @RequestParam("profileImageFile") MultipartFile file,
                             @RequestParam(value = "role", defaultValue = EazySchoolConstants.STUDENT_ROLE) String role,
                             HttpSession session) {
        if (errors.hasErrors()) {
            return "register.html";
        }
        if (!file.isEmpty()) {
            String profileImagePath = Utilities.uploadProfileImage(file, person);
            if (profileImagePath != null) {
                person.setProfileImagePath(profileImagePath);
            } else {
                log.error("Failed to upload profile image");
            }
        }

        // Added security
        boolean isSaved = false;
        Person admin = null;
        if (session != null) {
            admin = session.getAttribute("loggedInPerson") != null ? (Person) session.getAttribute("loggedInPerson") : null;
            if (admin != null) {
                isSaved = personService.createNewPerson(person, role);
            } else {
                isSaved = personService.createNewPerson(person, EazySchoolConstants.STUDENT_ROLE);
            }
        }

        if (isSaved) {
            if (admin != null) {
                return "redirect:/dashboard";
            }
            return "redirect:/login?register=true";
        } else {
            return "register.html";
        }
    }

}
