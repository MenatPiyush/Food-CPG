package com.food.cpg.salesorder;

import java.util.HashMap;
import java.util.Map;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultSalesOrderFactory extends SalesOrderFactory {

    private final ISalesOrderPersistence salesOrderPersistence;
    private final Map<String, SalesOrderStatus> salesOrderStatuses = new HashMap<>();

    public DefaultSalesOrderFactory(PersistenceFactory persistenceFactory) {
        salesOrderPersistence = persistenceFactory.getSalesOrderPersistence();

        salesOrderStatuses.put(SalesOrderStatus.Status.OPEN.name(), new SalesOpenOrderStatus());
        salesOrderStatuses.put(SalesOrderStatus.Status.PACKAGED.name(), new SalesPackagedOrderStatus());
        salesOrderStatuses.put(SalesOrderStatus.Status.SHIPPED.name(), new SalesShippedOrderStatus());
    }

    @Override
    public ISalesOrder makeSalesOrder() {
        return new SalesOrder();
    }

    @Override
    public ISalesOrderPersistence makeSalesOrderPersistence() {
        return salesOrderPersistence;
    }

    @Override
    public SalesOrderStatus makeOrderStatus(String orderStatus) {
        return salesOrderStatuses.get(orderStatus);
    }
}
