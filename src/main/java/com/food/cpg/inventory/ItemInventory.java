package com.food.cpg.inventory;

import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;

public class ItemInventory implements IItemInventory {
    private Integer itemId;
    private String itemName;
    private Double itemQuantity;
    private Integer manufacturerId;

    @Override
    public Integer getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public Double getItemQuantity() {
        return itemQuantity;
    }

    @Override
    public void setItemQuantity(Double itemQuantity) {
        this.itemQuantity = itemQuantity;
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
    public List<ItemInventory> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    @Override
    public void save() {
        getPersistence().save(this);
    }

    @Override
    public void increaseQuantity() {
        getPersistence().increaseQuantity(this);
    }

    @Override
    public void decreaseQuantity() {
        getPersistence().decreaseQuantity(this);
    }

    private IItemInventoryPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getItemInventoryPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
