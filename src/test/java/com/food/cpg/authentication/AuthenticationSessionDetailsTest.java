package com.food.cpg.authentication;

import org.junit.Assert;
import org.junit.Test;

public class AuthenticationSessionDetailsTest {

    private static final int AUTHENTICATED_USER_ID = 1;

    @Test
    public void getInstanceTest() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        Assert.assertNotNull(authenticationSessionDetails);
    }

    @Test
    public void getAuthenticatedUserIdTest() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        authenticationSessionDetails.setAuthenticatedUserId(AUTHENTICATED_USER_ID);

        int authenticatedUserId = authenticationSessionDetails.getAuthenticatedUserId();
        Assert.assertEquals(AUTHENTICATED_USER_ID, authenticatedUserId);
    }

    @Test
    public void sameAuthenticatedUserIdWithMultipleInstanceTest() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        authenticationSessionDetails.setAuthenticatedUserId(1);

        int authenticatedUserId = authenticationSessionDetails.getAuthenticatedUserId();
        Assert.assertEquals(AUTHENTICATED_USER_ID, authenticatedUserId);

        AuthenticationSessionDetails anotherSessionDetails = AuthenticationSessionDetails.getInstance();
        int authenticatedUserIdForAnotherInstance = anotherSessionDetails.getAuthenticatedUserId();
        Assert.assertEquals(AUTHENTICATED_USER_ID, authenticatedUserIdForAnotherInstance);
    }
}
