package com.food.cpg.purchaseorder;

public class PurchaseOpenOrderStatus extends PurchaseOrderStatus {

    public PurchaseOpenOrderStatus() {
        this.orderStatus = Status.OPEN;
    }

    @Override
    public void moveOrder(IPurchaseOrder purchaseOrder) {
        getPersistence().changeStatus(purchaseOrder.getOrderNumber(), Status.PLACED.name());
    }
}
