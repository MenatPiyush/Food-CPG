package com.food.cpg.registration;

public class RegistrationEndpoint {

    public static final String SHOW_REGISTRATIONS_END_POINT = "/show-registrations";
    public static final String APPROVE_REGISTRATIONS_END_POINT = "/approve-registration/{email}";
    public static final String BLOCK_REGISTRATIONS_END_POINT = "/block-registration/{email}";

    private RegistrationEndpoint() {
    }
}
