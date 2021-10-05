package com.food.cpg.manufacturer;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultManufacturerFactory extends ManufacturerFactory {

    private final IManufacturerPersistence manufacturerPersistence;

    public DefaultManufacturerFactory(PersistenceFactory persistenceFactory) {
        manufacturerPersistence = persistenceFactory.getManufacturerPersistence();
    }

    @Override
    public IManufacturer makeManufacturer() {
        return new Manufacturer();
    }

    @Override
    public IManufacturerPersistence makeManufacturerPersistence() {
        return manufacturerPersistence;
    }
}
