package com.food.cpg.purchaseorder;

import java.util.List;

public interface IPurchaseOrderPersistence {

    void save(IPurchaseOrder purchaseOrder);

    void delete(IPurchaseOrder purchaseOrder);

    void changeStatus(String orderNumber, String orderStatus);

    void load(IPurchaseOrder purchaseOrder);

    List<IPurchaseOrder> getOpenPurchaseOrder(int manufacturerId);

    List<IPurchaseOrder> getPlacedPurchaseOrder(int manufacturerId);

    List<IPurchaseOrder> getReceivedPurchaseOrder(int manufacturerId);

    List<IPurchaseOrder> getPaidPurchaseOrder(int manufacturerId);
}