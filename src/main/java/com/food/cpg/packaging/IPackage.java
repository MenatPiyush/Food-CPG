package com.food.cpg.packaging;

public interface IPackage {

    Integer getPackageId();

    void setPackageId(Integer packageId);

    Integer getItemId();

    void setItemId(Integer itemId);

    String getPackageName();

    void setPackageName(String packageName);

    Double getQuantity();

    void setQuantity(Double quantity);

    Double getManufacturingCost();

    void setManufacturingCost(Double manufacturingCost);

    Double getWholesaleCost();

    void setWholesaleCost(Double wholesaleCost);

    Double getRetailCost();

    void setRetailCost(Double retailCost);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    void load();
}
