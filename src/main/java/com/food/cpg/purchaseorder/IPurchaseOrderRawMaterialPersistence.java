package com.food.cpg.purchaseorder;

import java.util.List;

public interface IPurchaseOrderRawMaterialPersistence {

    void save(IPurchaseOrderRawMaterial purchaseOrderRawMaterials);

    List<PurchaseOrderRawMaterial> getPurchaseOrderItemRawMaterial(int itemId);

    List<PurchaseOrderRawMaterial> getPurchaseOrderRawMaterials(String orderNumber);

    void delete(String purchaseOrderNumber);
}