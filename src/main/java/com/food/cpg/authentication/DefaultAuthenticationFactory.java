package com.food.cpg.authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultAuthenticationFactory extends AuthenticationFactory {

    @Override
    public IAuthNavigator makeAuthNavigator() {
        return new AuthNavigator();
    }

    @Override
    public PasswordEncoder makePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
