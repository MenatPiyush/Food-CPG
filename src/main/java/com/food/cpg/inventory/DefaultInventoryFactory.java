package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultInventoryFactory extends InventoryFactory {

    private final IRawMaterialInventoryPersistence rawMaterialInventoryPersistence;
    private final IItemInventoryPersistence itemInventoryPersistence;

    public DefaultInventoryFactory(PersistenceFactory persistenceFactory) {
        rawMaterialInventoryPersistence = persistenceFactory.getRawMaterialInventoryPersistence();
        itemInventoryPersistence = persistenceFactory.getItemInventoryPersistence();
    }

    @Override
    public IRawMaterialInventory makeRawMaterialInventory() {
        return new RawMaterialInventory();
    }

    @Override
    public IRawMaterialInventoryPersistence makeRawMaterialInventoryPersistence() {
        return rawMaterialInventoryPersistence;
    }

    @Override
    public IItemInventory makeItemInventory() {
        return new ItemInventory();
    }

    @Override
    public IItemInventoryPersistence makeItemInventoryPersistence() {
        return itemInventoryPersistence;
    }
}
