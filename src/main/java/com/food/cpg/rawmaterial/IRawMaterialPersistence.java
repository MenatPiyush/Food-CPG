package com.food.cpg.rawmaterial;

import java.util.List;

public interface IRawMaterialPersistence {

    List<IRawMaterial> getAll(int manufacturerId);

    void load(IRawMaterial rawMaterial);

    Integer save(IRawMaterial rawMaterial);

    void update(IRawMaterial rawMaterial);

    void delete(int rawMaterialId);
}