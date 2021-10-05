package com.food.cpg.item;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.inventory.ItemInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item implements IItem {

    private Integer id;
    private Integer manufacturerId;
    private String name;
    private Double cookingCost;
    private Double totalCost;
    private List<ItemRawMaterial> itemRawMaterials;

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
    public Double getCookingCost() {
        return cookingCost;
    }

    @Override
    public void setCookingCost(Double cookingCost) {
        this.cookingCost = cookingCost;
    }

    @Override
    public Double getTotalCost() {
        return totalCost;
    }

    @Override
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public List<ItemRawMaterial> getItemRawMaterials() {
        return itemRawMaterials;
    }

    @Override
    public void setItemRawMaterials(List<ItemRawMaterial> itemRawMaterials) {
        this.itemRawMaterials = itemRawMaterials;
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
    public List<IItem> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    @Override
    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);
        Integer itemId = getPersistence().save(this);
        for (IItemRawMaterial itemRawMaterial : getItemRawMaterials()) {
            itemRawMaterial.setItemId(itemId);
            itemRawMaterial.save();
        }
        saveItemInventory(itemId);
    }

    @Override
    public void saveItemInventory(int itemId) {
        ItemInventory itemInventory = new ItemInventory();
        itemInventory.setItemId(itemId);
        itemInventory.setItemQuantity(0.0);
        itemInventory.save();
    }

    @Override
    public void load() {
        if (this.getId() > 0) {
            getPersistence().load(this);
        }
    }

    @Override
    public void delete() {
        getPersistence().delete(this.getId());
    }

    @Override
    public void addItemRawMaterial(ItemRawMaterial itemRawMaterial) {
        if (this.itemRawMaterials == null) {
            this.itemRawMaterials = new ArrayList<>();
        }
        Double cost = itemRawMaterial.calculateCost();
        itemRawMaterial.setCost(cost);
        this.itemRawMaterials.add(itemRawMaterial);
    }

    @Override
    public void calculateTotalCost() {
        List<ItemRawMaterial> itemRawMaterials = this.getItemRawMaterials();
        Double total = this.getCookingCost();
        for (ItemRawMaterial itemRawMaterial : itemRawMaterials) {
            total += itemRawMaterial.getCost();
        }
        this.setTotalCost(total);
    }

    private IItemPersistence getPersistence() {
        return ItemFactory.instance().makeItemPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
