package com.food.cpg.manufactureorder;

import com.food.cpg.databasepersistence.PersistenceFactory;

public abstract class ManufactureOrderStatus {

    enum Status {
        OPEN, MANUFACTURED, PACKAGED, STORED
    }

    protected ManufactureOrderStatus.Status orderStatus;

    public ManufactureOrderStatus.Status getOrderStatus() {
        return this.orderStatus;
    }

    public abstract void moveOrder(IManufactureOrder manufactureOrder);

    protected IManufactureOrderPersistence getPersistence() {
        return ManufactureOrderFactory.instance().makeManufactureOrderPersistence();
    }

}
