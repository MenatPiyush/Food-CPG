package com.food.cpg.manufacturer;

import java.util.List;

public interface IManufacturerPersistence {

    List<IManufacturer> getAll();

    IManufacturer get(String manufacturerEmail);

    void load(IManufacturer manufacturer);

    void register(IManufacturer manufacturer);

    void createLoginAccount(IManufacturer manufacturer);
}