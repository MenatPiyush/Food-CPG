package com.food.cpg.manufactureorder;

public abstract class ManufactureOrderFactory {

    private static ManufactureOrderFactory manufactureOrderFactory;

    public static ManufactureOrderFactory instance() {
        return manufactureOrderFactory;
    }

    public static void setManufactureOrderFactory(ManufactureOrderFactory factory) {
        manufactureOrderFactory = factory;
    }

    public abstract IManufactureOrder makeManufactureOrder();

    public abstract IManufactureOrderPersistence makeManufactureOrderPersistence();

    public abstract ManufactureOrderStatus makeOrderStatus(String orderStatus);
}
