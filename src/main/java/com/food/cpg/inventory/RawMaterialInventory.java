package com.food.cpg.inventory;

import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;

public class RawMaterialInventory implements IRawMaterialInventory {

    private Integer rawMaterialId;
    private String rawMaterialName;
    private String vendorName;
    private Double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;
    private Integer manufacturerId;
    private String manufacturerEmail;

    @Override
    public Integer getRawMaterialId() {
        return rawMaterialId;
    }

    @Override
    public void setRawMaterialId(Integer rawMaterialId) {
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
    public String getVendorName() {
        return vendorName;
    }

    @Override
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public Double getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    @Override
    public void setRawMaterialQuantity(Double rawMaterialQuantity) {
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
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    @Override
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public String getManufacturerEmail() {
        return manufacturerEmail;
    }

    @Override
    public void setManufacturerEmail(String manufacturerEmail) {
        this.manufacturerEmail = manufacturerEmail;
    }

    @Override
    public List<IRawMaterialInventory> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    @Override
    public void save(Integer rawMaterialId) {
        getPersistence().save(rawMaterialId);
    }


    @Override
    public void increaseQuantity() {
        Unit inventoryUnit = Unit.from(this.getRawMaterialQuantityUOM());
        double quantityInGram = inventoryUnit.getGramValue() * this.getRawMaterialQuantity();
        this.setRawMaterialQuantity(quantityInGram);

        getPersistence().increaseQuantity(this);
    }

    @Override
    public void decreaseQuantity() {
        Unit inventoryUnit = Unit.from(this.getRawMaterialQuantityUOM());
        double quantityInGram = inventoryUnit.getGramValue() * this.getRawMaterialQuantity();
        this.setRawMaterialQuantity(quantityInGram);

        getPersistence().decreaseQuantity(this);
    }

    private IRawMaterialInventoryPersistence getPersistence() {
        return InventoryFactory.instance().makeRawMaterialInventoryPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
