package com.food.cpg.purchaseorder;

import com.food.cpg.rawmaterial.IRawMaterial;

public interface IPurchaseOrderRawMaterial {
    String getPurchaseOrderNumber();

    void setPurchaseOrderNumber(String purchaseOrderNumber);

    int getRawMaterialId();

    void setRawMaterialId(int rawMaterialId);

    String getRawMaterialName();

    void setRawMaterialName(String rawMaterialName);

    double getRawMaterialCost();

    void setRawMaterialCost(double rawMaterialCost);

    double getRawMaterialQuantity();

    void setRawMaterialQuantity(double rawMaterialQuantity);

    String getRawMaterialQuantityUOM();

    void setRawMaterialQuantityUOM(String rawMaterialQuantityUOM);

    void save();

    void loadDetails(IRawMaterial rawMaterial);

    void delete();
}
