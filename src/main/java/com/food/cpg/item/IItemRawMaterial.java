package com.food.cpg.item;

public interface IItemRawMaterial {

    public Integer getItemId();

    public void setItemId(Integer itemId);

    public Integer getRawMaterialId();

    public void setRawMaterialId(Integer rawMaterialId);

    public Integer getVendorId();

    public void setVendorId(Integer vendorId);

    public Double getRawMaterialQuantity();

    public void setRawMaterialQuantity(Double rawMaterialQuantity);

    public String getRawMaterialQuantityUOM();

    public void setRawMaterialQuantityUOM(String rawMaterialQuantityUOM);

    public Double getRawMaterialUnitCost();

    public void setRawMaterialUnitCost(Double rawMaterialUnitCost);

    public Double getCost();

    public void setCost(Double cost);

    public void save();

    public void delete();

    public void loadUnitCost();

    public Double calculateCost();
}
