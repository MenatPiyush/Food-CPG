package com.food.cpg.purchaseorder;

public abstract class PurchaseOrderFactory {

    private static PurchaseOrderFactory purchaseOrderFactory;

    public static PurchaseOrderFactory instance() {
        return purchaseOrderFactory;
    }

    public static void setPurchaseOrderFactory(PurchaseOrderFactory factory) {
        purchaseOrderFactory = factory;
    }

    public abstract IPurchaseOrder makePurchaseOrder();

    public abstract PurchaseOrderRawMaterial makePurchaseOrderRawMaterial();

    public abstract IPurchaseOrderPersistence makePurchaseOrderPersistence();

    public abstract IPurchaseOrderRawMaterialPersistence makePurchaseOrderRawMaterialPersistence();

    public abstract PurchaseOrderStatus makeOrderStatus(String orderStatus);
}
