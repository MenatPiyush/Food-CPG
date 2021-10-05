package com.food.cpg.inventory;

import java.util.List;

public interface IRawMaterialInventoryPersistence {

    List<IRawMaterialInventory> getAll(int manufacturerId);

    void increaseQuantity(IRawMaterialInventory rawMaterialInventory);

    void decreaseQuantity(IRawMaterialInventory rawMaterialInventory);

    void save(int rawMaterialId);
}

