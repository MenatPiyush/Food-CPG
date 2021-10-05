package com.food.cpg.registration;

import java.util.List;

import com.food.cpg.manufacturer.IManufacturer;

public interface IRegistration {
    IManufacturer getManufacturer();

    void setManufacturer(IManufacturer manufacturer);

    String getStatus();

    void setStatus(String status);

    List<IRegistration> getAll();

    void approve(String email);

    void block(String email);
}
