package com.food.cpg.registration;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RegistrationController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_REGISTRATIONS_ROUTE = "registration/show-registrations";
    private static final String EMAIL_PATH_VARIABLE_NAME = "email";
    private static final String VIEW_REGISTRATIONS_KEY = "registrations";

    @GetMapping(RegistrationEndpoint.SHOW_REGISTRATIONS_END_POINT)
    public String showRegistrations(Registration registration, Model model) {
        List<IRegistration> registrations = registration.getAll();
        model.addAttribute(VIEW_REGISTRATIONS_KEY, registrations);
        return SHOW_REGISTRATIONS_ROUTE;
    }

    @GetMapping(RegistrationEndpoint.APPROVE_REGISTRATIONS_END_POINT)
    public String approveManufacturer(@PathVariable(EMAIL_PATH_VARIABLE_NAME) String email, Registration registration) {
        registration.approve(email);
        return redirectToShowRegistrations();
    }

    @GetMapping(RegistrationEndpoint.BLOCK_REGISTRATIONS_END_POINT)
    public String blockManufacturer(@PathVariable(EMAIL_PATH_VARIABLE_NAME) String email, Registration registration) {
        registration.block(email);
        return redirectToShowRegistrations();
    }

    private String redirectToShowRegistrations() {
        return REDIRECT_NOTATION + RegistrationEndpoint.SHOW_REGISTRATIONS_END_POINT;
    }
}