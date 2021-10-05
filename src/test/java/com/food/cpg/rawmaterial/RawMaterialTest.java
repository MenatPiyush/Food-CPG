package com.food.cpg.rawmaterial;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.inventory.IRawMaterialInventory;
import com.food.cpg.inventory.InventoryFactory;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RawMaterial.class, InventoryFactory.class})
public class RawMaterialTest {

    private static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final String GET_LOGGED_IN_MANUFACTURER_ID_METHOD = "getLoggedInManufacturerId";
    private static final String GET_INSTANCE_METHOD = "instance";
    private static final double DELTA = 1e-15;
    private static final String EMPTY_STRING = "";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String VENDOR_ATTRIBUTE = "vendor";
    private static final String UNIT_COST_ATTRIBUTE = "unitCost";
    private static final String UNIT_MEASUREMENT_ATTRIBUTE = "unitMeasurement";
    private static final String REORDER_QUANTITY_ATTRIBUTE = "reorderPointQuantity";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_RAW_MATERIAL_ID = 1;
    private static final String TEST_RAW_MATERIAL_UNIT = "g";
    private static final String TEST_RAW_MATERIAL_NAME = "Test raw material 1";

    @Mock
    IRawMaterial rawMaterial;

    @Mock
    IRawMaterialPersistence rawMaterialPersistence;

    @Mock
    InventoryFactory inventoryFactory;

    @Mock
    IRawMaterialInventory rawMaterialInventory;


    @Test
    public void isValidRawMaterialNameTest() {
        IRawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(EMPTY_STRING);

        boolean isValidName = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get(NAME_ATTRIBUTE));
    }

    @Test
    public void isValidRawMaterialVendorTest() {
        IRawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setVendorId(null);

        boolean isValidVendor = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidVendor);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get(VENDOR_ATTRIBUTE));
    }

    @Test
    public void isValidRawMaterialUnitCostTest() {
        IRawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setUnitCost(null);

        boolean isValidUnitCost = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidUnitCost);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get(UNIT_COST_ATTRIBUTE));
    }

    @Test
    public void isValidRawMaterialUnitMeasurementTest() {
        IRawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setUnitMeasurement(null);
        rawMaterial.setUnitMeasurementUOM(TEST_RAW_MATERIAL_UNIT);

        boolean isValidUnitMeasurement = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidUnitMeasurement);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get(UNIT_MEASUREMENT_ATTRIBUTE));
    }

    @Test
    public void isValidRawMaterialUnitMeasurementUOMTest() {
        IRawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setUnitMeasurement(20.5);
        rawMaterial.setUnitMeasurementUOM(null);

        boolean isValidUnitMeasurement = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidUnitMeasurement);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get(UNIT_MEASUREMENT_ATTRIBUTE));
    }

    @Test
    public void isValidRawMaterialReorderPointTest() {
        IRawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setReorderPointQuantity(null);
        rawMaterial.setReorderPointQuantityUOM(TEST_RAW_MATERIAL_UNIT);

        boolean isValidReorderPoint = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidReorderPoint);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get(REORDER_QUANTITY_ATTRIBUTE));
    }

    @Test
    public void isValidRawMaterialReorderPointUOMTest() {
        IRawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setReorderPointQuantity(25.10);
        rawMaterial.setReorderPointQuantityUOM(null);

        boolean isValidReorderPoint = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidReorderPoint);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get(REORDER_QUANTITY_ATTRIBUTE));
    }

    @Test
    public void getAllTest() throws Exception {
        IRawMaterial rawMaterial = spy(new RawMaterial());
        rawMaterial.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterial.setName(TEST_RAW_MATERIAL_NAME);

        List<IRawMaterial> rawMaterials = new ArrayList<>();
        rawMaterials.add(rawMaterial);

        PowerMockito.doReturn(rawMaterialPersistence).when(rawMaterial, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(rawMaterials).when(rawMaterialPersistence).getAll(anyInt());

        List<IRawMaterial> rawMaterialsResult = rawMaterial.getAll();
        Assert.assertNotNull(rawMaterialsResult);
        Assert.assertEquals(1, rawMaterialsResult.size());
        Assert.assertEquals(TEST_RAW_MATERIAL_NAME, rawMaterialsResult.get(0).getName());
    }

    @Test
    public void saveTest() throws Exception {
        IRawMaterial rawMaterial = spy(new RawMaterial());
        rawMaterial.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterial.setName(TEST_RAW_MATERIAL_NAME);

        PowerMockito.doReturn(rawMaterialPersistence).when(rawMaterial, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(TEST_RAW_MATERIAL_ID).when(rawMaterialPersistence).save(rawMaterial);
        PowerMockito.doReturn(1).when(rawMaterial, GET_LOGGED_IN_MANUFACTURER_ID_METHOD);
        PowerMockito.doNothing().when(rawMaterial).saveRawMaterialInventory(TEST_RAW_MATERIAL_ID);

        rawMaterial.save();
        verifyPrivate(rawMaterial).invoke(GET_PERSISTENCE_METHOD);
        verify(rawMaterialPersistence, times(1)).save(rawMaterial);
    }

    @Test
    public void saveRawMaterialInventory() throws Exception {
        PowerMockito.mockStatic(InventoryFactory.class);
        PowerMockito.doReturn(inventoryFactory).when(InventoryFactory.class, GET_INSTANCE_METHOD);
        when(inventoryFactory.makeRawMaterialInventory()).thenReturn(rawMaterialInventory);

        PowerMockito.doNothing().when(rawMaterialInventory).save(TEST_RAW_MATERIAL_ID);

        rawMaterial.saveRawMaterialInventory(TEST_RAW_MATERIAL_ID);

        verify(rawMaterial, times(1)).saveRawMaterialInventory(TEST_RAW_MATERIAL_ID);
    }

    @Test
    public void loadSuccessTest() throws Exception {
        IRawMaterial rawMaterial = spy(new RawMaterial());
        rawMaterial.setId(TEST_RAW_MATERIAL_ID);
        rawMaterial.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterial.setName(TEST_RAW_MATERIAL_NAME);

        PowerMockito.doReturn(rawMaterialPersistence).when(rawMaterial, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(rawMaterialPersistence).load(rawMaterial);

        rawMaterial.load();
        verifyPrivate(rawMaterial).invoke(GET_PERSISTENCE_METHOD);
        verify(rawMaterialPersistence, times(1)).load(rawMaterial);
    }

    @Test
    public void loadFailTest() throws Exception {
        IRawMaterial rawMaterial = spy(new RawMaterial());
        rawMaterial.setId(0);
        rawMaterial.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterial.setName(TEST_RAW_MATERIAL_NAME);

        PowerMockito.doReturn(rawMaterialPersistence).when(rawMaterial, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(rawMaterialPersistence).load(rawMaterial);

        rawMaterial.load();
        verify(rawMaterialPersistence, times(0)).load(rawMaterial);
    }

    @Test
    public void updateTest() throws Exception {
        IRawMaterial rawMaterial = spy(new RawMaterial());
        rawMaterial.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterial.setName(TEST_RAW_MATERIAL_NAME);

        PowerMockito.doReturn(rawMaterialPersistence).when(rawMaterial, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(rawMaterialPersistence).update(rawMaterial);

        rawMaterial.update();
        verifyPrivate(rawMaterial).invoke(GET_PERSISTENCE_METHOD);
        verify(rawMaterialPersistence, times(1)).update(rawMaterial);
    }

    @Test
    public void deleteTest() throws Exception {
        IRawMaterial rawMaterial = spy(new RawMaterial());
        rawMaterial.setId(TEST_RAW_MATERIAL_ID);
        rawMaterial.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterial.setName(TEST_RAW_MATERIAL_NAME);

        PowerMockito.doReturn(rawMaterialPersistence).when(rawMaterial, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(rawMaterialPersistence).delete(anyInt());

        rawMaterial.delete();
        verifyPrivate(rawMaterial).invoke(GET_PERSISTENCE_METHOD);
        verify(rawMaterialPersistence, times(1)).delete(rawMaterial.getId());
    }

    @Test
    public void getCostTest() {
        IRawMaterial rawMaterial = spy(new RawMaterial());
        rawMaterial.setId(TEST_RAW_MATERIAL_ID);
        rawMaterial.setUnitCost(20.0);

        List<IRawMaterial> rawMaterials = new ArrayList<>();
        rawMaterials.add(rawMaterial);

        PowerMockito.doReturn(rawMaterials).when(rawMaterial).getAll();

        double cost = rawMaterial.getCost(TEST_RAW_MATERIAL_ID);

        Assert.assertEquals(20.0, cost, DELTA);
    }
}
