package com.food.cpg.manufacturer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ManufacturerController {

    private static final String MANUFACTURER_REGISTER_FORM_ROUTE = "manufacturer/register-manufacturer";
    private static final String REGISTER_MANUFACTURER_ROUTE = "manufacturer/manufacturer-registration-request";
    private static final String MANUFACTURER_DETAIL_ROUTE = "manufacturer/manufacturer-details";
    private static final String VIEW_MANUFACTURER_KEY = "manufacturer";
    private static final String EMAIL_PATH_VARIABLE_NAME = "email";

    @GetMapping(ManufacturerEndpoint.MANUFACTURER_REGISTER_FORM_END_POINT)
    public String registrationForm(Manufacturer manufacturer) {
        return MANUFACTURER_REGISTER_FORM_ROUTE;
    }

    @PostMapping(ManufacturerEndpoint.SAVE_MANUFACTURER_END_POINT)
    public String saveManufacturer(Manufacturer manufacturer, BindingResult result) {
        if (result.hasErrors() || !manufacturer.isValidManufacturer()) {
            return MANUFACTURER_REGISTER_FORM_ROUTE;
        }

        manufacturer.register();
        return REGISTER_MANUFACTURER_ROUTE;
    }

    @GetMapping(ManufacturerEndpoint.MANUFACTURER_DETAIL_END_POINT)
    public String details(@PathVariable(EMAIL_PATH_VARIABLE_NAME) String email, Manufacturer manufacturer, Model model) {
        manufacturer.setEmail(email);
        manufacturer.load();
        model.addAttribute(VIEW_MANUFACTURER_KEY, manufacturer);
        return MANUFACTURER_DETAIL_ROUTE;
    }
}
