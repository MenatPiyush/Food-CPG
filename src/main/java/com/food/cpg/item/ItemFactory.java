package com.food.cpg.item;

public abstract class ItemFactory {

    private static ItemFactory itemFactory;

    public static ItemFactory instance() {
        return itemFactory;
    }

    public static void setItemFactory(ItemFactory factory) {
        itemFactory = factory;
    }

    public abstract IItem makeItem();

    public abstract Object makeItemRawMaterial();

    public abstract IItemPersistence makeItemPersistence();

    public abstract IItemRawMaterialPersistence makeItemRawMaterialPersistence();
}
