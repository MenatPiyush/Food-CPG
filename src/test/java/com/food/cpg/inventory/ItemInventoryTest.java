package com.food.cpg.inventory;

import junit.framework.TestCase;

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
@PrepareForTest(ItemInventory.class)
public class ItemInventoryTest extends TestCase {

    public static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer ITEM_ID = 1;
    private static final String ITEM_NAME = "Test item";

    @Mock
    IItemInventoryPersistence itemInventoryPersistence;

    @Test
    public void getAllTest() throws Exception {
        ItemInventory itemInventory = spy(new ItemInventory());
        itemInventory.setManufacturerId(TEST_MANUFACTURER_ID);
        itemInventory.setItemId(ITEM_ID);
        itemInventory.setItemName(ITEM_NAME);

        List<ItemInventory> itemInventoryList = new ArrayList<>();
        itemInventoryList.add(itemInventory);

        PowerMockito.doReturn(itemInventoryPersistence).when(itemInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(itemInventoryList).when(itemInventoryPersistence).getAll(anyInt());

        List<ItemInventory> itemInventoriesResult = itemInventory.getAll();
        Assert.assertNotNull(itemInventoriesResult);
        Assert.assertEquals(1, itemInventoriesResult.size());
        Assert.assertEquals(ITEM_NAME, itemInventoriesResult.get(0).getItemName());
    }

    public void increaseQuantityTest() throws Exception {
        ItemInventory itemInventory = spy(new ItemInventory());

        PowerMockito.doReturn(itemInventoryPersistence).when(itemInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(itemInventoryPersistence).increaseQuantity(itemInventory);

        itemInventory.increaseQuantity();
        verifyPrivate(itemInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(itemInventoryPersistence, times(1)).increaseQuantity(itemInventory);
    }

    @Test
    public void decreaseQuantityTest() throws Exception {
        ItemInventory itemInventory = spy(new ItemInventory());

        PowerMockito.doReturn(itemInventoryPersistence).when(itemInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(itemInventoryPersistence).decreaseQuantity(itemInventory);

        itemInventory.decreaseQuantity();
        verifyPrivate(itemInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(itemInventoryPersistence, times(1)).decreaseQuantity(itemInventory);
    }

    @Test
    public void saveTest() throws Exception {
        ItemInventory itemInventory = spy(new ItemInventory());

        PowerMockito.doReturn(itemInventoryPersistence).when(itemInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(itemInventoryPersistence).save(itemInventory);

        itemInventory.save();
        verifyPrivate(itemInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(itemInventoryPersistence, times(1)).save(itemInventory);
    }

}
