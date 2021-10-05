package com.food.cpg.salesorder;

public class SalesOpenOrderStatus extends SalesOrderStatus {

    public SalesOpenOrderStatus() {
        this.orderStatus = Status.OPEN;
    }

    @Override
    public void moveOrder(ISalesOrder salesOrder) {
        getPersistence().changeStatus(salesOrder.getOrderNumber(), Status.PACKAGED.name());
    }
}
