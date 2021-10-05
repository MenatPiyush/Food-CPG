package com.food.cpg.manufacturer;

public abstract class ManufacturerFactory {

    private static ManufacturerFactory manufacturerFactory;

    public static ManufacturerFactory instance() {
        return manufacturerFactory;
    }

    public static void setManufacturerFactory(ManufacturerFactory factory) {
        manufacturerFactory = factory;
    }

    public abstract IManufacturer makeManufacturer();

    public abstract IManufacturerPersistence makeManufacturerPersistence();
}
