package com.food.cpg.item;

import java.util.List;

public interface IItemPersistence {

    List<IItem> getAll(int manufacturerId);

    void load(IItem item);

    Integer save(IItem item);

    void delete(int itemId);

}
