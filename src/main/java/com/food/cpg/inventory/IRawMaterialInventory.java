package com.food.cpg.inventory;

import java.util.List;

public interface IRawMaterialInventory {
    Integer getRawMaterialId();

    void setRawMaterialId(Integer rawMaterialId);

    String getRawMaterialName();

    void setRawMaterialName(String rawMaterialName);

    String getVendorName();

    void setVendorName(String vendorName);

    Double getRawMaterialQuantity();

    void setRawMaterialQuantity(Double rawMaterialQuantity);

    String getRawMaterialQuantityUOM();

    void setRawMaterialQuantityUOM(String rawMaterialQuantityUOM);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    String getManufacturerEmail();

    void setManufacturerEmail(String manufacturerEmail);

    List<IRawMaterialInventory> getAll();

    void save(Integer rawMaterialId);

    void increaseQuantity();

    void decreaseQuantity();
}
