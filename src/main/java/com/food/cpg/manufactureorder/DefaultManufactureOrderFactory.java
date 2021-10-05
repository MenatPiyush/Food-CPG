package com.food.cpg.manufactureorder;

import com.food.cpg.databasepersistence.PersistenceFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultManufactureOrderFactory extends ManufactureOrderFactory {

    private final IManufactureOrderPersistence manufactureOrderPersistence;
    private final Map<String, ManufactureOrderStatus> manufactureOrderStatuses = new HashMap<>();

    public DefaultManufactureOrderFactory(PersistenceFactory persistenceFactory) {
        manufactureOrderPersistence = persistenceFactory.getManufactureOrderPersistence();

        manufactureOrderStatuses.put(ManufactureOrderStatus.Status.OPEN.name(), new ManufactureOpenOrderStatus());
        manufactureOrderStatuses.put(ManufactureOrderStatus.Status.MANUFACTURED.name(), new ManufactureManufacturedOrderStatus());
        manufactureOrderStatuses.put(ManufactureOrderStatus.Status.PACKAGED.name(), new ManufacturePackagedOrderStatus());
    }

    @Override
    public IManufactureOrder makeManufactureOrder() {
        return new ManufactureOrder();
    }

    @Override
    public IManufactureOrderPersistence makeManufactureOrderPersistence() {
        return manufactureOrderPersistence;
    }

    @Override
    public ManufactureOrderStatus makeOrderStatus(String orderStatus) {
        return manufactureOrderStatuses.get(orderStatus);
    }
}
