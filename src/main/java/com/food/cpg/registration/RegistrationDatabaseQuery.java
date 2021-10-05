package com.food.cpg.registration;

public class RegistrationDatabaseQuery {

    public static final String SELECT_ALL_REGISTRATIONS = "select * from manufacturer m left join users u on m.manufacturer_email = u.email";
    public static final String APPROVE_REGISTRATION = "update users set status = 'Approved' where email = ?";
    public static final String BLOCK_REGISTRATION = "update users set status = 'Blocked' where email = ?";

    private RegistrationDatabaseQuery() {
    }
}
