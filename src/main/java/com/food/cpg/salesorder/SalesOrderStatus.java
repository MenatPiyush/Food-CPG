package com.food.cpg.salesorder;

public abstract class SalesOrderStatus {

    enum Status {
        OPEN, PACKAGED, SHIPPED, PAID
    }

    protected Status orderStatus;

    public Status getOrderStatus() {
        return this.orderStatus;
    }

    public abstract void moveOrder(ISalesOrder salesOrder);

    protected ISalesOrderPersistence getPersistence() {
        return SalesOrderFactory.instance().makeSalesOrderPersistence();
    }
}
