package com.food.cpg.authentication;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    private static final String UNAUTHORIZED_ERROR_PAGE_ROUTE = "/403-error";

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        redirectStrategy.sendRedirect(request, response, UNAUTHORIZED_ERROR_PAGE_ROUTE);
    }
}
