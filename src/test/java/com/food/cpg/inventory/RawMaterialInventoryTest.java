package com.food.cpg.inventory;

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
@PrepareForTest(RawMaterialInventory.class)
public class RawMaterialInventoryTest {

    public static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_RAW_MATERIAL_ID = 1;
    private static final String TEST_RAW_MATERIAL_NAME = "Test raw material";
    private static final Double TEST_RAW_MATERIAL_QUANTITY = 10.0;

    @Mock
    IRawMaterialInventoryPersistence rawMaterialInventoryPersistence;

    @Test
    public void getAllTest() throws Exception {
        IRawMaterialInventory rawMaterialInventory = spy(new RawMaterialInventory());
        rawMaterialInventory.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterialInventory.setRawMaterialId(TEST_RAW_MATERIAL_ID);
        rawMaterialInventory.setRawMaterialName(TEST_RAW_MATERIAL_NAME);

        List<IRawMaterialInventory> rawMaterialInventories = new ArrayList<>();
        rawMaterialInventories.add(rawMaterialInventory);

        PowerMockito.doReturn(rawMaterialInventoryPersistence).when(rawMaterialInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(rawMaterialInventories).when(rawMaterialInventoryPersistence).getAll(anyInt());

        List<IRawMaterialInventory> rawMaterialInventoriesResult = rawMaterialInventory.getAll();
        Assert.assertNotNull(rawMaterialInventoriesResult);
        Assert.assertEquals(1, rawMaterialInventoriesResult.size());
        Assert.assertEquals(TEST_RAW_MATERIAL_NAME, rawMaterialInventoriesResult.get(0).getRawMaterialName());
    }

    @Test
    public void increaseQuantityTest() throws Exception {
        IRawMaterialInventory rawMaterialInventory = spy(new RawMaterialInventory());
        rawMaterialInventory.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterialInventory.setRawMaterialName(TEST_RAW_MATERIAL_NAME);
        rawMaterialInventory.setRawMaterialQuantity(TEST_RAW_MATERIAL_QUANTITY);

        PowerMockito.doReturn(rawMaterialInventoryPersistence).when(rawMaterialInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(rawMaterialInventoryPersistence).increaseQuantity(rawMaterialInventory);

        rawMaterialInventory.increaseQuantity();
        verifyPrivate(rawMaterialInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(rawMaterialInventoryPersistence, times(1)).increaseQuantity(rawMaterialInventory);
    }

    @Test
    public void decreaseQuantityTest() throws Exception {
        IRawMaterialInventory rawMaterialInventory = spy(new RawMaterialInventory());
        rawMaterialInventory.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterialInventory.setRawMaterialName(TEST_RAW_MATERIAL_NAME);
        rawMaterialInventory.setRawMaterialQuantity(TEST_RAW_MATERIAL_QUANTITY);

        PowerMockito.doReturn(rawMaterialInventoryPersistence).when(rawMaterialInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(rawMaterialInventoryPersistence).decreaseQuantity(rawMaterialInventory);

        rawMaterialInventory.decreaseQuantity();
        verifyPrivate(rawMaterialInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(rawMaterialInventoryPersistence, times(1)).decreaseQuantity(rawMaterialInventory);
    }

}