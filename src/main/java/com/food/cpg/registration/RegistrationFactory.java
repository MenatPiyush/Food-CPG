package com.food.cpg.registration;

public abstract class RegistrationFactory {

    private static RegistrationFactory registrationFactory;

    public static RegistrationFactory instance() {
        return registrationFactory;
    }

    public static void setRegistrationFactory(RegistrationFactory factory) {
        registrationFactory = factory;
    }

    public abstract IRegistration makeRegistration();

    public abstract IRegistrationPersistence makeRegistrationPersistence();
}
