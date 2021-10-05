package com.food.cpg.purchaseorder;

import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.rawmaterial.IRawMaterial;

public class PurchaseOrderByItem {

    private Integer itemId;
    private Double itemQuantity;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void createPurchaseOrderByItem(IRawMaterial rawMaterial) {
        IPurchaseOrder purchaseOrder = PurchaseOrderFactory.instance().makePurchaseOrder();

        List<PurchaseOrderRawMaterial> purchaseOrderItemRawMaterials = getPurchaseOrderItemRawMaterial(this.getItemId());
        for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : purchaseOrderItemRawMaterials) {
            double requiredQuantityPerItem = purchaseOrderRawMaterial.getRawMaterialQuantity();
            purchaseOrderRawMaterial.setRawMaterialQuantity(getItemQuantity() * requiredQuantityPerItem);
            purchaseOrderRawMaterial.loadDetails(rawMaterial);
            purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);
        }

        purchaseOrder.setManufacturerId(this.getLoggedInManufacturerId());
        purchaseOrder.save();
    }

    private IPurchaseOrderRawMaterialPersistence getPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderRawMaterialPersistence();
    }

    public List<PurchaseOrderRawMaterial> getPurchaseOrderItemRawMaterial(int itemId) {
        return getPersistence().getPurchaseOrderItemRawMaterial(itemId);
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
