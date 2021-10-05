package com.food.cpg.authentication;

public class AuthenticationEndpoint {

    public static final String[] PUBLIC_END_POINTS = new String[]{"/", "/css/**", "/image/**", "/js/**", "/webjars/**", "/favicon.ico", "/*-error", "/register-manufacturer", "/save-manufacturer", "manufacturer/manufacturer-registration-request"};
    public static final String[] ADMIN_END_POINTS = new String[]{"/show-registrations*", "/approve-registration/*", "/block-registration/*", "/manufacturer-details/*"};
    public static final String LOGIN_END_POINT = "/login";
    public static final String ROOT_END_POINT = "/";
    public static final String UNAUTHORIZED_ERROR_PAGE_END_POINT = "/403-error";

    private AuthenticationEndpoint() {
    }
}
