package com.food.cpg.item;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultItemFactory extends ItemFactory {

    private final IItemPersistence itemPersistence;
    private final IItemRawMaterialPersistence itemRawMaterialPersistence;

    public DefaultItemFactory(PersistenceFactory persistenceFactory) {
        itemPersistence = persistenceFactory.getItemPersistence();
        itemRawMaterialPersistence = persistenceFactory.getItemRawMaterialPersistence();
    }

    @Override
    public IItem makeItem() {
        return new Item();
    }

    @Override
    public IItemPersistence makeItemPersistence() {
        return itemPersistence;
    }

    @Override
    public IItemRawMaterial makeItemRawMaterial() {
        return new ItemRawMaterial();
    }

    @Override
    public IItemRawMaterialPersistence makeItemRawMaterialPersistence() {
        return itemRawMaterialPersistence;
    }
}
