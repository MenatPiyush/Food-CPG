package com.food.cpg.inventory;

import java.util.List;

public interface IItemInventory {
    Integer getItemId();

    void setItemId(Integer itemId);

    String getItemName();

    void setItemName(String itemName);

    Double getItemQuantity();

    void setItemQuantity(Double itemQuantity);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    List<ItemInventory> getAll();

    void save();

    void increaseQuantity();

    void decreaseQuantity();
}
