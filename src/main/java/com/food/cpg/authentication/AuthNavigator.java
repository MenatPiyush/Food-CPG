package com.food.cpg.authentication;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.ManufacturerFactory;

public class AuthNavigator implements IAuthNavigator {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void navigateToLandingPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (final GrantedAuthority grantedAuthority : authorities) {
                String authorityName = grantedAuthority.getAuthority();
                Role userRole = Role.getRole(authorityName);

                if (userRole == Role.NONE) {
                    continue;
                }

                if (userRole == Role.MANUFACTURER) {
                    loadManufacturerSessionDetails(authentication);
                }

                String targetUrl = userRole.getLandingPage();

                redirectStrategy.sendRedirect(request, response, targetUrl);
            }
        }
    }

    private void loadManufacturerSessionDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInUserEmail = userDetails.getUsername();

        IManufacturerPersistence manufacturerPersistence = ManufacturerFactory.instance().makeManufacturerPersistence();
        IManufacturer manufacturer = manufacturerPersistence.get(loggedInUserEmail);

        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        authenticationSessionDetails.setAuthenticatedUserId(manufacturer.getId());
    }
}
