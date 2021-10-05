package com.food.cpg.purchaseorder;

public abstract class PurchaseOrderStatus {
    enum Status {
        OPEN, PLACED, RECEIVED, PAID
    }

    protected Status orderStatus;

    public Status getOrderStatus() {
        return this.orderStatus;
    }

    public abstract void moveOrder(IPurchaseOrder purchaseOrder);

    protected IPurchaseOrderPersistence getPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderPersistence();
    }
}
