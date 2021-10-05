package com.food.cpg.inventory;

public abstract class InventoryFactory {
    private static InventoryFactory inventoryFactory;

    public static InventoryFactory instance() {
        return inventoryFactory;
    }

    public static void setInventoryFactory(InventoryFactory factory) {
        inventoryFactory = factory;
    }

    public abstract IRawMaterialInventory makeRawMaterialInventory();

    public abstract IRawMaterialInventoryPersistence makeRawMaterialInventoryPersistence();

    public abstract IItemInventory makeItemInventory();

    public abstract IItemInventoryPersistence makeItemInventoryPersistence();
}
