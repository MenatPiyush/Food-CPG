package com.food.cpg.authentication;

public class AuthenticationQuery {

    public static final String USERS_BY_USERNAME = "select email, password, '1' as status from users where status = 'Approved' and email = ?";
    public static final String AUTHORITIES_BY_USERNAME = "select email, role from users where status = 'Approved' and email = ?";

    private AuthenticationQuery() {
    }
}
