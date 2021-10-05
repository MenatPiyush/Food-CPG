package com.food.cpg.purchaseorder;

import com.food.cpg.rawmaterial.IRawMaterial;

public class PurchaseOrderRawMaterial implements IPurchaseOrderRawMaterial {

    private String purchaseOrderNumber;
    private int rawMaterialId;
    private String rawMaterialName;
    private double rawMaterialCost;
    private double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;

    @Override
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    @Override
    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    @Override
    public int getRawMaterialId() {
        return rawMaterialId;
    }

    @Override
    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    @Override
    public String getRawMaterialName() {
        return rawMaterialName;
    }

    @Override
    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    @Override
    public double getRawMaterialCost() {
        return rawMaterialCost;
    }

    @Override
    public void setRawMaterialCost(double rawMaterialCost) {
        this.rawMaterialCost = rawMaterialCost;
    }

    @Override
    public double getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    @Override
    public void setRawMaterialQuantity(double rawMaterialQuantity) {
        this.rawMaterialQuantity = rawMaterialQuantity;
    }

    @Override
    public String getRawMaterialQuantityUOM() {
        return rawMaterialQuantityUOM;
    }

    @Override
    public void setRawMaterialQuantityUOM(String rawMaterialQuantityUOM) {
        this.rawMaterialQuantityUOM = rawMaterialQuantityUOM;
    }

    @Override
    public void save() {
        getPersistence().save(this);
    }

    @Override
    public void loadDetails(IRawMaterial rawMaterial) {
        rawMaterial.setId(this.getRawMaterialId());
        rawMaterial.load();

        double totalCost = rawMaterial.getUnitCost() * getRawMaterialQuantity();
        this.setRawMaterialCost(totalCost);
        this.setRawMaterialName(rawMaterial.getName());
    }

    @Override
    public void delete() {
        getPersistence().delete(this.getPurchaseOrderNumber());
    }

    private IPurchaseOrderRawMaterialPersistence getPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderRawMaterialPersistence();
    }
}
