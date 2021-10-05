package com.food.cpg.purchaseorder;

import java.util.HashMap;
import java.util.Map;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultPurchaseOrderFactory extends PurchaseOrderFactory {

    private final IPurchaseOrderPersistence purchaseOrderPersistence;
    private final IPurchaseOrderRawMaterialPersistence purchaseOrderRawMaterialPersistence;
    private final Map<String, PurchaseOrderStatus> purchaseOrderStatuses = new HashMap<>();

    public DefaultPurchaseOrderFactory(PersistenceFactory persistenceFactory) {
        purchaseOrderPersistence = persistenceFactory.getPurchaseOrderPersistence();
        purchaseOrderRawMaterialPersistence = persistenceFactory.getPurchaseOrderRawMaterialPersistence();

        purchaseOrderStatuses.put(PurchaseOrderStatus.Status.OPEN.name(), new PurchaseOpenOrderStatus());
        purchaseOrderStatuses.put(PurchaseOrderStatus.Status.PLACED.name(), new PurchasePlacedOrderStatus());
        purchaseOrderStatuses.put(PurchaseOrderStatus.Status.RECEIVED.name(), new PurchaseReceivedOrderStatus());
    }

    @Override
    public IPurchaseOrder makePurchaseOrder() {
        return new PurchaseOrder();
    }

    @Override
    public PurchaseOrderRawMaterial makePurchaseOrderRawMaterial() {
        return new PurchaseOrderRawMaterial();
    }

    @Override
    public IPurchaseOrderPersistence makePurchaseOrderPersistence() {
        return purchaseOrderPersistence;
    }

    @Override
    public IPurchaseOrderRawMaterialPersistence makePurchaseOrderRawMaterialPersistence() {
        return purchaseOrderRawMaterialPersistence;
    }

    @Override
    public PurchaseOrderStatus makeOrderStatus(String orderStatus) {
        return purchaseOrderStatuses.get(orderStatus);
    }
}
