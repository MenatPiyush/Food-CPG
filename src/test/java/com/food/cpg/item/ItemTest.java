package com.food.cpg.item;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Item.class)
public class ItemTest {

    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_ITEM_ID = 1;
    private static final String TEST_ITEM_NAME = "Test Vendor 1";
    private static final Double TEST_COOKING_COST = 3.0;
    private static final Double TEST_TOTAL_COST = 10.0;
    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final String GET_MANUFACTURER_ID_METHOD_NAME = "getLoggedInManufacturerId";
    private static final double DELTA = 1e-15;
    private static final Double TEST_ITEM_QUANTITY = 0.0;

    @Mock
    IItemPersistence itemPersistence;

    @Test
    public void getAllTest() throws Exception {
        IItem item = spy(new Item());
        item.setManufacturerId(TEST_MANUFACTURER_ID);
        item.setName(TEST_ITEM_NAME);
        item.setCookingCost(TEST_COOKING_COST);

        List<IItem> itemList = new ArrayList<>();
        itemList.add(item);

        PowerMockito.doReturn(itemPersistence).when(item, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doReturn(itemList).when(itemPersistence).getAll(anyInt());

        List<IItem> itemResult = item.getAll();
        Assert.assertNotNull(itemResult);
        Assert.assertEquals(1, itemList.size());
        Assert.assertEquals(TEST_ITEM_NAME, itemList.get(0).getName());
    }

    @Test
    public void loadSuccessTest() throws Exception {
        IItem item = spy(new Item());
        item.setId(TEST_ITEM_ID);
        item.setManufacturerId(TEST_MANUFACTURER_ID);
        item.setName(TEST_ITEM_NAME);

        PowerMockito.doReturn(itemPersistence).when(item, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(itemPersistence).load(item);

        item.load();
        verifyPrivate(item).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(itemPersistence, times(1)).load(item);
    }

    @Test
    public void loadFailTest() throws Exception {
        IItem item = spy(new Item());
        item.setId(0);
        item.setManufacturerId(TEST_MANUFACTURER_ID);
        item.setName(TEST_ITEM_NAME);

        PowerMockito.doReturn(itemPersistence).when(item, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(itemPersistence).load(item);

        item.load();
        verify(itemPersistence, times(0)).load(item);
    }

    @Test
    public void saveTest() throws Exception {
        IItem item = spy(new Item());
        item.setId(TEST_ITEM_ID);
        item.setTotalCost(TEST_TOTAL_COST);

        ItemRawMaterial itemRawMaterial = spy(new ItemRawMaterial());
        itemRawMaterial.setItemId(TEST_ITEM_ID);

        List<ItemRawMaterial> itemRawMaterialList = new ArrayList<>();
        itemRawMaterialList.add(itemRawMaterial);

        item.setItemRawMaterials(itemRawMaterialList);

        PowerMockito.doReturn(itemPersistence).when(item, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doReturn(1).when(itemPersistence).save(item);
        PowerMockito.doReturn(1).when(item, GET_MANUFACTURER_ID_METHOD_NAME);
        PowerMockito.doReturn(itemRawMaterialList).when(item).getItemRawMaterials();
        PowerMockito.doNothing().when(item).addItemRawMaterial(itemRawMaterial);
        PowerMockito.doNothing().when(item).saveItemInventory(anyInt());
        PowerMockito.doNothing().when(itemRawMaterial).save();
        PowerMockito.doNothing().when(itemRawMaterial).setItemId(anyInt());

        item.save();
        verifyPrivate(item).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(itemPersistence, times(1)).save(item);
    }

    @Test
    public void deleteTest() throws Exception {
        IItem item = spy(new Item());
        item.setId(TEST_ITEM_ID);
        item.setManufacturerId(TEST_MANUFACTURER_ID);
        item.setName(TEST_ITEM_NAME);

        PowerMockito.doReturn(itemPersistence).when(item, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(itemPersistence).delete(anyInt());

        item.delete();
        verifyPrivate(item).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(itemPersistence, times(1)).delete(item.getId());
    }

    @Test
    public void addItemRawMaterialTest() throws Exception {
        IItem item = spy(new Item());
        ItemRawMaterial itemRawMaterial = spy(new ItemRawMaterial());
        item.setId(1);

        Assert.assertNull(item.getItemRawMaterials());

        PowerMockito.doReturn(10.0).when(itemRawMaterial).calculateCost();

        item.addItemRawMaterial(itemRawMaterial);

        List<ItemRawMaterial> itemRawMaterials = item.getItemRawMaterials();
        Assert.assertNotNull(itemRawMaterials);
        Assert.assertEquals(1, itemRawMaterials.size());
        Assert.assertEquals(10.0, itemRawMaterials.get(0).getCost().doubleValue(), DELTA);
    }

    @Test
    public void calculateTotalCostTest() {
        IItem item = spy(new Item());
        item.setCookingCost(10.0);

        ItemRawMaterial itemRawMaterial = spy(new ItemRawMaterial());
        itemRawMaterial.setItemId(1);
        itemRawMaterial.setCost(10.0);
        List<ItemRawMaterial> itemRawMaterialList = new ArrayList<>();
        itemRawMaterialList.add(itemRawMaterial);
        item.setItemRawMaterials(itemRawMaterialList);

        item.calculateTotalCost();
        Assert.assertEquals(20.0, item.getTotalCost().doubleValue(), DELTA);
    }

}