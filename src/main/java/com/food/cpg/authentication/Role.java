package com.food.cpg.authentication;

public enum Role {
    ADMIN("/show-registrations"),
    MANUFACTURER("/vendors"),
    NONE("/login");

    private final String landingPage;

    Role(String landingPage) {
        this.landingPage = landingPage;
    }

    public static Role getRole(String authority) {
        for (Role role : values()) {
            if (role.name().equals(authority)) {
                return role;
            }
        }
        return NONE;
    }

    public String getLandingPage() {
        return landingPage;
    }
}
