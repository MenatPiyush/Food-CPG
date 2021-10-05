package com.food.cpg.packaging;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Package implements IPackage {
    private Integer packageId;
    private Integer itemId;
    private Integer manufacturerId;
    private String packageName;
    private Double quantity;
    private Double manufacturingCost;
    private Double wholesaleCost;
    private Double retailCost;

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Integer getPackageId() {
        return packageId;
    }

    @Override
    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    @Override
    public Integer getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public Double getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public Double getManufacturingCost() {
        return manufacturingCost;
    }

    @Override
    public void setManufacturingCost(Double manufacturingCost) {
        this.manufacturingCost = manufacturingCost;
    }

    @Override
    public Double getWholesaleCost() {
        return wholesaleCost;
    }

    @Override
    public void setWholesaleCost(Double wholesaleCost) {
        this.wholesaleCost = wholesaleCost;
    }

    @Override
    public Double getRetailCost() {
        return retailCost;
    }

    @Override
    public void setRetailCost(Double retailCost) {
        this.retailCost = retailCost;
    }

    @Override
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    @Override
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValidPackage() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(this.getPackageName())) {
            errors.put("packageName", "Package name is required.");
            isValid = false;
        }

        if (this.getItemId() == null) {
            errors.put("item", "Valid item is required.");
            isValid = false;
        }

        if (this.getQuantity() == null) {
            errors.put("quantity", "Quantity is required.");
            isValid = false;
        }

        if (this.getManufacturingCost() == null) {
            errors.put("manufacturingCost", "Manufacturing cost is required.");
            isValid = false;
        }

        if (this.getWholesaleCost() == null) {
            errors.put("wholesaleCost", "Wholesale cost is required.");
            isValid = false;
        }

        if (this.getRetailCost() == null) {
            errors.put("retailCost", "Retail cost is required.");
            isValid = false;
        }

        return isValid;
    }

    public List<IPackage> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        getPersistence().save(this);
    }

    @Override
    public void load() {
        if (this.getPackageId() > 0) {
            getPersistence().load(this);
        }
    }

    public void update() {
        getPersistence().update(this);
    }

    public void delete() {
        getPersistence().delete(this.getPackageId());
    }

    private IPackagePersistence getPersistence() {
        return PackageFactory.instance().makePackagePersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }

}
