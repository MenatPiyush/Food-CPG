package com.food.cpg.authentication;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAuthNavigator {
    void navigateToLandingPage(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
