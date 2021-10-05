package com.food.cpg.registration;

import java.util.List;

public interface IRegistrationPersistence {

    List<IRegistration> getAll();

    void approve(String email);

    void block(String email);
}