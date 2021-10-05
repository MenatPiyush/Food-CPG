package com.food.cpg.salesorder;

public abstract class SalesOrderFactory {

    private static SalesOrderFactory salesOrderFactory;

    public static SalesOrderFactory instance() {
        return salesOrderFactory;
    }

    public static void setSalesOrderFactory(SalesOrderFactory factory) {
        salesOrderFactory = factory;
    }

    public abstract ISalesOrder makeSalesOrder();

    public abstract ISalesOrderPersistence makeSalesOrderPersistence();

    public abstract SalesOrderStatus makeOrderStatus(String orderStatus);
}
