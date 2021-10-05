package com.food.cpg.authentication;

import org.junit.Assert;
import org.junit.Test;

public class RoleTest {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String ADMIN_LANDING_PAGE = "/show-registrations";
    private static final String MANUFACTURER_ROLE = "MANUFACTURER";
    private static final String MANUFACTURER_LANDING_PAGE = "/vendors";
    private static final String ANONYMOUS_ROLE = "ANONYMOUS";
    private static final String NONE_LANDING_PAGE = "/login";

    @Test
    public void getAdminRoleTest() {
        Role role = Role.getRole(ADMIN_ROLE);

        Assert.assertEquals(Role.ADMIN, role);
    }

    @Test
    public void getManufacturerRoleTest() {
        Role role = Role.getRole(MANUFACTURER_ROLE);

        Assert.assertEquals(Role.MANUFACTURER, role);
    }

    @Test
    public void getAnonymousRoleTest() {
        Role role = Role.getRole(ANONYMOUS_ROLE);

        Assert.assertEquals(Role.NONE, role);
    }

    @Test
    public void getAdminLandingPageTest() {
        Role role = Role.getRole(ADMIN_ROLE);
        String landingPage = role.getLandingPage();

        Assert.assertEquals(ADMIN_LANDING_PAGE, landingPage);
    }

    @Test
    public void getManufacturerLandingPageTest() {
        Role role = Role.getRole(MANUFACTURER_ROLE);
        String landingPage = role.getLandingPage();

        Assert.assertEquals(MANUFACTURER_LANDING_PAGE, landingPage);
    }

    @Test
    public void getAnonymousLandingPageTest() {
        Role role = Role.getRole(ANONYMOUS_ROLE);
        String landingPage = role.getLandingPage();

        Assert.assertEquals(NONE_LANDING_PAGE, landingPage);
    }
}
