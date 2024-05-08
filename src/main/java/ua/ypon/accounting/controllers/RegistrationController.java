package ua.ypon.accounting.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.ypon.accounting.models.Person;
import ua.ypon.accounting.services.RegistrationService;
import ua.ypon.accounting.util.PersonValidator;

@Controller
@RequestMapping()
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService service;
    private PersonValidator personValidator;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "/registration";
        service.register(person);
        return "redirect: /login";
    }

}
