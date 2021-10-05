package com.food.cpg.rawmaterial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.inventory.IRawMaterialInventory;
import com.food.cpg.inventory.InventoryFactory;

public class RawMaterial implements IRawMaterial {
    private Integer id;
    private Integer manufacturerId;
    private String name;
    private Integer vendorId;
    private Double unitCost;
    private Double unitMeasurement;
    private String unitMeasurementUOM;
    private Double reorderPointQuantity;
    private String reorderPointQuantityUOM;

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    @Override
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getVendorId() {
        return vendorId;
    }

    @Override
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public Double getUnitCost() {
        return unitCost;
    }

    @Override
    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    @Override
    public Double getUnitMeasurement() {
        return unitMeasurement;
    }

    @Override
    public void setUnitMeasurement(Double unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    @Override
    public String getUnitMeasurementUOM() {
        return unitMeasurementUOM;
    }

    @Override
    public void setUnitMeasurementUOM(String unitMeasurementUOM) {
        this.unitMeasurementUOM = unitMeasurementUOM;
    }

    @Override
    public Double getReorderPointQuantity() {
        return reorderPointQuantity;
    }

    @Override
    public void setReorderPointQuantity(Double reorderPointQuantity) {
        this.reorderPointQuantity = reorderPointQuantity;
    }

    @Override
    public String getReorderPointQuantityUOM() {
        return reorderPointQuantityUOM;
    }

    @Override
    public void setReorderPointQuantityUOM(String reorderPointQuantityUOM) {
        this.reorderPointQuantityUOM = reorderPointQuantityUOM;
    }

    @Override
    public Map<String, String> getErrors() {
        return errors;
    }

    @Override
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean isValidRawMaterial() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(this.getName())) {
            errors.put("name", "Raw material name is required.");
            isValid = false;
        }

        if (this.getVendorId() == null) {
            errors.put("vendor", "Valid vendor is required.");
            isValid = false;
        }

        if (this.getUnitCost() == null) {
            errors.put("unitCost", "Unit cost is required.");
            isValid = false;
        }

        if (this.getUnitMeasurement() == null || StringUtils.isEmpty(this.getUnitMeasurementUOM())) {
            errors.put("unitMeasurement", "Unit measurement is required.");
            isValid = false;
        }

        if (this.getReorderPointQuantity() == null || StringUtils.isEmpty(this.getReorderPointQuantityUOM())) {
            errors.put("reorderPointQuantity", "Reorder point quantity is required.");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public List<IRawMaterial> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    @Override
    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        Integer rawMaterialId = getPersistence().save(this);

        saveRawMaterialInventory(rawMaterialId);
    }

    @Override
    public void saveRawMaterialInventory(Integer rawMaterialID) {
        IRawMaterialInventory rawMaterialInventory = InventoryFactory.instance().makeRawMaterialInventory();
        rawMaterialInventory.save(rawMaterialID);
    }

    @Override
    public void load() {
        if (this.getId() > 0) {
            getPersistence().load(this);
        }
    }

    @Override
    public void update() {
        getPersistence().update(this);
    }

    @Override
    public void delete() {
        getPersistence().delete(this.getId());
    }

    @Override
    public double getCost(int rawMaterialId) {
        List<IRawMaterial> rawMaterials = getAll();
        for (IRawMaterial rawMaterial : rawMaterials) {
            if (rawMaterial.getId() == rawMaterialId) {
                return rawMaterial.getUnitCost();
            }
        }
        return 0.0;
    }

    private IRawMaterialPersistence getPersistence() {
        return RawMaterialFactory.instance().makeRawMaterialPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
