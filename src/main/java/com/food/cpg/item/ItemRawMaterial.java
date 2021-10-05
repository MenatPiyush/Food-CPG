package com.food.cpg.item;

public class ItemRawMaterial implements IItemRawMaterial {

    private Integer itemId;
    private Integer rawMaterialId;
    private Integer vendorId;
    private Double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;
    private Double rawMaterialUnitCost;
    private Double cost;

    @Override
    public Integer getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public Integer getRawMaterialId() {
        return rawMaterialId;
    }

    @Override
    public void setRawMaterialId(Integer rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
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
    public Double getRawMaterialUnitCost() {
        return rawMaterialUnitCost;
    }

    @Override
    public void setRawMaterialUnitCost(Double rawMaterialUnitCost) {
        this.rawMaterialUnitCost = rawMaterialUnitCost;
    }

    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public void save() {
        getPersistence().save(this);
    }

    @Override
    public void delete() {
        getPersistence().delete(this.getItemId());
    }

    @Override
    public void loadUnitCost() {
        Integer rawMaterialId = getRawMaterialId();
        Double unitCost = getPersistence().loadUnitCost(rawMaterialId);
        setRawMaterialUnitCost(unitCost);
    }

    @Override
    public Double calculateCost() {
        loadUnitCost();
        Double totalCost = getRawMaterialQuantity() * getRawMaterialUnitCost();
        return totalCost;
    }

    private IItemRawMaterialPersistence getPersistence() {
        return ItemFactory.instance().makeItemRawMaterialPersistence();
    }
}
