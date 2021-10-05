package com.food.cpg.registration;

import java.util.List;

import com.food.cpg.manufacturer.IManufacturer;

public class Registration implements IRegistration {

    private IManufacturer manufacturer;
    private String status;

    @Override
    public IManufacturer getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(IManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public List<IRegistration> getAll() {
        return getPersistence().getAll();
    }

    @Override
    public void approve(String email) {
        getPersistence().approve(email);
    }

    @Override
    public void block(String email) {
        getPersistence().block(email);
    }

    private IRegistrationPersistence getPersistence() {
        return RegistrationFactory.instance().makeRegistrationPersistence();
    }
}
