package com.food.cpg.vendor;

public class VendorEndpoint {

    public static final String VENDOR_LIST_REQUEST_END_POINT = "/vendors";
    public static final String ADD_VENDOR_END_POINT = "/add-vendor";
    public static final String SAVE_VENDOR_END_POINT = "/save-vendor";
    public static final String EDIT_VENDOR_END_POINT = "/save-vendor/{vendorId}";
    public static final String EDIT_VENDOR_FORM_END_POINT = "/vendors/edit/{vendorId}";
    public static final String DELETE_VENDOR_FORM_END_POINT = "/vendors/delete/{vendorId}";

    private VendorEndpoint() {
    }
}
