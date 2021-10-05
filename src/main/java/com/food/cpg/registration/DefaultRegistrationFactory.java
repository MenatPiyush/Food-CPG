package com.food.cpg.registration;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultRegistrationFactory extends RegistrationFactory {

    private final IRegistrationPersistence registrationPersistence;

    public DefaultRegistrationFactory(PersistenceFactory persistenceFactory) {
        registrationPersistence = persistenceFactory.getRegistrationPersistence();
    }

    @Override
    public IRegistration makeRegistration() {
        return new Registration();
    }

    @Override
    public IRegistrationPersistence makeRegistrationPersistence() {
        return registrationPersistence;
    }
}
