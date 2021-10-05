package com.food.cpg.item;

public interface IItemRawMaterialPersistence {

    void save(IItemRawMaterial itemRawMaterial);

    void delete(int itemId);

    Double loadUnitCost(Integer rawMaterialId);
}
