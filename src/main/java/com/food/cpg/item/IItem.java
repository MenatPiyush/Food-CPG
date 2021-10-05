package com.food.cpg.item;

import java.util.List;
import java.util.Map;

public interface IItem {

    Integer getId();

    void setId(Integer id);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    String getName();

    void setName(String name);

    Double getCookingCost();

    void setCookingCost(Double cookingCost);

    Double getTotalCost();

    void setTotalCost(Double totalCost);

    List<ItemRawMaterial> getItemRawMaterials();

    void setItemRawMaterials(List<ItemRawMaterial> itemRawMaterials);

    Map<String, String> getErrors();

    void setErrors(Map<String, String> errors);

    List<IItem> getAll();

    void save();

    void saveItemInventory(int itemId);

    void load();

    void delete();

    void addItemRawMaterial(ItemRawMaterial itemRawMaterial);

    void calculateTotalCost();
}
