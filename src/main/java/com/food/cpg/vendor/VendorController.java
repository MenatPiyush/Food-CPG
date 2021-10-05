package com.food.cpg.vendor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VendorController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String VENDOR_LIST_ROUTE = "vendor/vendors";
    private static final String NEW_VENDOR_FORM_ROUTE = "vendor/add-vendor";
    private static final String EDIT_VENDOR_FORM_ROUTE = "vendor/edit-vendor";
    private static final String VIEW_VENDORS_KEY = "vendors";
    private static final String VENDOR_ID_PATH_VARIABLE_NAME = "vendorId";

    @GetMapping(VendorEndpoint.VENDOR_LIST_REQUEST_END_POINT)
    public String showVendors(Vendor vendor, Model model) {
        List<IVendor> vendorList = vendor.getAll();
        model.addAttribute(VIEW_VENDORS_KEY, vendorList);

        return VENDOR_LIST_ROUTE;
    }

    @GetMapping(VendorEndpoint.ADD_VENDOR_END_POINT)
    public String showAddVendorForm(Vendor vendor) {
        return NEW_VENDOR_FORM_ROUTE;
    }

    @PostMapping(VendorEndpoint.SAVE_VENDOR_END_POINT)
    public String saveVendor(Vendor vendor, BindingResult result) {
        if (result.hasErrors() || !vendor.isValidVendor()) {
            return NEW_VENDOR_FORM_ROUTE;
        }

        vendor.save();
        return redirectToVendorList();
    }

    @PostMapping(VendorEndpoint.EDIT_VENDOR_END_POINT)
    public String editVendor(Vendor vendor, BindingResult result) {
        if (result.hasErrors() || !vendor.isValidVendor()) {
            return EDIT_VENDOR_FORM_ROUTE;
        }

        vendor.update();
        return redirectToVendorList();
    }

    @GetMapping(VendorEndpoint.EDIT_VENDOR_FORM_END_POINT)
    public String showEditVendorForm(@PathVariable(VENDOR_ID_PATH_VARIABLE_NAME) int vendorId, Vendor vendor) {
        vendor.setId(vendorId);
        vendor.load();

        return EDIT_VENDOR_FORM_ROUTE;
    }

    @GetMapping(VendorEndpoint.DELETE_VENDOR_FORM_END_POINT)
    public String deleteVendor(@PathVariable(VENDOR_ID_PATH_VARIABLE_NAME) int vendorId, Vendor vendor) {
        vendor.setId(vendorId);
        vendor.delete();

        return redirectToVendorList();
    }

    private String redirectToVendorList() {
        return REDIRECT_NOTATION + VendorEndpoint.VENDOR_LIST_REQUEST_END_POINT;
    }
}