package com.food.cpg.item;

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
@PrepareForTest(ItemRawMaterial.class)
public class ItemRawMaterialTest {

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final Integer TEST_ITEM_ID = 1;
    private static final Integer TEST_RAW_MATERIAL_ID = 1;
    private static final Double TEST_RAW_MATERIAL_QUANTITY = 10.0;

    @Mock
    IItemRawMaterialPersistence itemRawMaterialPersistence;

    @Test
    public void saveTest() throws Exception {
        IItemRawMaterial itemRawMaterial = spy(new ItemRawMaterial());

        PowerMockito.doReturn(itemRawMaterialPersistence).when(itemRawMaterial, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(itemRawMaterialPersistence).save(itemRawMaterial);

        itemRawMaterial.save();
        verifyPrivate(itemRawMaterial).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(itemRawMaterialPersistence, times(1)).save(itemRawMaterial);
    }

    @Test
    public void deleteTest() throws Exception {
        IItemRawMaterial itemRawMaterial = spy(new ItemRawMaterial());
        itemRawMaterial.setItemId(TEST_ITEM_ID);

        PowerMockito.doReturn(itemRawMaterialPersistence).when(itemRawMaterial, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(itemRawMaterialPersistence).delete(anyInt());

        itemRawMaterial.delete();
        verifyPrivate(itemRawMaterial).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(itemRawMaterialPersistence, times(1)).delete(itemRawMaterial.getItemId());
    }

    @Test
    public void loadUnitCostTest() throws Exception {
        IItemRawMaterial itemRawMaterial = spy(new ItemRawMaterial());

        PowerMockito.doReturn(itemRawMaterialPersistence).when(itemRawMaterial, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doReturn(30.0).when(itemRawMaterialPersistence).loadUnitCost(TEST_RAW_MATERIAL_ID);

        itemRawMaterial.loadUnitCost();
        verifyPrivate(itemRawMaterial).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(itemRawMaterial, times(1)).loadUnitCost();
    }

    @Test
    public void calculateCostTest() throws Exception {
        IItemRawMaterial itemRawMaterial = spy(new ItemRawMaterial());
        itemRawMaterial.setRawMaterialQuantity(TEST_RAW_MATERIAL_QUANTITY);

        PowerMockito.doReturn(itemRawMaterialPersistence).when(itemRawMaterial, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doReturn(30.0).when(itemRawMaterialPersistence).loadUnitCost(TEST_RAW_MATERIAL_ID);

        Double cost = itemRawMaterial.calculateCost();
        verifyPrivate(itemRawMaterial).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(itemRawMaterial, times(1)).loadUnitCost();
        Assert.assertNotNull(cost);
    }
}
