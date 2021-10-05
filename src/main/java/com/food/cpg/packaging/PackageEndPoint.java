package com.food.cpg.packaging;

public class PackageEndPoint {

    public static final String SHOW_PACKAGES_END_POINT = "/packages";
    public static final String SHOW_ADD_PACKAGE_FORM_END_POINT = "/add-packages";
    public static final String SAVE_PACKAGE_END_POINT = "/save-packages";
    public static final String EDIT_PACKAGE_END_POINT = "/save-packages/{packageId}";
    public static final String SHOW_EDIT_PACKAGE_FORM_END_POINT = "/packages/edit/{packageId}";
    public static final String DELETE_PACKAGE_END_POINT = "/packages/delete/{packageId}";

    private PackageEndPoint() {

    }
}
