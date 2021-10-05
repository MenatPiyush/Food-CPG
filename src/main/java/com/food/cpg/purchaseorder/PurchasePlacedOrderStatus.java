package com.food.cpg.purchaseorder;

import java.util.List;

import com.food.cpg.inventory.IRawMaterialInventory;
import com.food.cpg.inventory.InventoryFactory;

public class PurchasePlacedOrderStatus extends PurchaseOrderStatus {

    public PurchasePlacedOrderStatus() {
        this.orderStatus = Status.PLACED;
    }

    @Override
    public void moveOrder(IPurchaseOrder purchaseOrder) {
        getPersistence().changeStatus(purchaseOrder.getOrderNumber(), Status.RECEIVED.name());

        increaseRawMaterialQuantity(purchaseOrder);
    }

    public void increaseRawMaterialQuantity(IPurchaseOrder purchaseOrder) {
        List<PurchaseOrderRawMaterial> purchaseOrderRawMaterialsDetail = getPurchaseOrderRawMaterialPersistence().getPurchaseOrderRawMaterials(purchaseOrder.getOrderNumber());
        purchaseOrder.setPurchaseOrderRawMaterials(purchaseOrderRawMaterialsDetail);

        for (IPurchaseOrderRawMaterial purchaseOrderRawMaterial : purchaseOrder.getPurchaseOrderRawMaterials()) {
            IRawMaterialInventory rawMaterialInventory = InventoryFactory.instance().makeRawMaterialInventory();
            rawMaterialInventory.setRawMaterialId(purchaseOrderRawMaterial.getRawMaterialId());
            rawMaterialInventory.setRawMaterialQuantity(purchaseOrderRawMaterial.getRawMaterialQuantity());
            rawMaterialInventory.setRawMaterialQuantityUOM(purchaseOrderRawMaterial.getRawMaterialQuantityUOM());

            rawMaterialInventory.increaseQuantity();
        }
    }

    private IPurchaseOrderRawMaterialPersistence getPurchaseOrderRawMaterialPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderRawMaterialPersistence();
    }
}
