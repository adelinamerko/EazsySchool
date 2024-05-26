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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Controller
@RequestMapping("public")
public class PublicController {

    private final PersonService personService;

    @Autowired
    public PublicController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register.html";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors errors,
                             @RequestParam("profileImageFile") MultipartFile file) {
        if (errors.hasErrors()) {
            return "register.html";
        }
        if (!file.isEmpty()) {
            String profileImagePath = Utilities.uploadProfileImage(file, person);
            person.setProfileImagePath(profileImagePath);
        }

        boolean isSaved = personService.createNewPerson(person, EazySchoolConstants.STUDENT_ROLE);

        if (isSaved) {
            return "redirect:/login?register=true";
        } else {
            return "register.html";
        }
    }

}
