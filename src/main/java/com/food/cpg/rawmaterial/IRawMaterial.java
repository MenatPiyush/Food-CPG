package com.food.cpg.rawmaterial;

import java.util.List;
import java.util.Map;

public interface IRawMaterial {
    Integer getId();

    void setId(Integer id);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    String getName();

    void setName(String name);

    Integer getVendorId();

    void setVendorId(Integer vendorId);

    Double getUnitCost();

    void setUnitCost(Double unitCost);

    Double getUnitMeasurement();

    void setUnitMeasurement(Double unitMeasurement);

    String getUnitMeasurementUOM();

    void setUnitMeasurementUOM(String unitMeasurementUOM);

    Double getReorderPointQuantity();

    void setReorderPointQuantity(Double reorderPointQuantity);

    String getReorderPointQuantityUOM();

    void setReorderPointQuantityUOM(String reorderPointQuantityUOM);

    Map<String, String> getErrors();

    void setErrors(Map<String, String> errors);

    boolean isValidRawMaterial();

    List<IRawMaterial> getAll();

    void save();

    void saveRawMaterialInventory(Integer rawMaterialID);

    void load();

    void update();

    void delete();

    double getCost(int rawMaterialId);
}
