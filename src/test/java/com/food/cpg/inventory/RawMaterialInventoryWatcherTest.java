package com.food.cpg.inventory;

import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.Manufacturer;
import com.food.cpg.notification.INotification;
import com.food.cpg.notification.NotificationFactory;
import com.food.cpg.purchaseorder.IPurchaseOrder;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.IPurchaseOrderRawMaterialPersistence;
import com.food.cpg.purchaseorder.PurchaseOrderFactory;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterial;
import com.food.cpg.rawmaterial.IRawMaterial;
import com.food.cpg.rawmaterial.IRawMaterialPersistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RawMaterialInventoryWatcher.class, NotificationFactory.class, PurchaseOrderFactory.class})
public class RawMaterialInventoryWatcherTest {

    private static final String GET_MANUFACTURER_PERSISTENCE_METHOD = "getManufacturerPersistence";
    private static final String GET_RAW_MATERIAL_INVENTORY_PERSISTENCE_METHOD = "getRawMaterialInventoryPersistence";
    private static final String GET_RAW_MATERIAL_PERSISTENCE_METHOD = "getRawMaterialPersistence";
    private static final String GET_PURCHASE_ORDER_PERSISTENCE_METHOD = "getPurchaseOrderPersistence";
    private static final String GET_PURCHASE_ORDER_RAW_MATERIAL_PERSISTENCE_METHOD = "getPurchaseOrderRawMaterialPersistence";
    private static final String GET_INSTANCE_METHOD = "instance";
    private static final int TEST_MANUFACTURER_ID = 1;
    private static final int TEST_RAW_MATERIAL_ID = 1;
    private static final String TEST_RAW_MATERIAL_NAME= "Test raw material";
    private static final String TEST_RAW_MATERIAL_UNIT_MEASUREMENT_UOM= "g";
    private static final Double TEST_RAW_MATERIAL_UNIT_COST= 10.00;
    private static final int TEST_VENDOR_ID = 1;
    private static final Double TEST_RAW_MATERIAL_INVENTORY_QUANTITY = 8.00;
    private static final Double TEST_RAW_MATERIAL_REORDER_POINT = 10.00;

    @Mock
    INotification notification;

    @Mock
    NotificationFactory notificationFactory;

    @Mock
    IManufacturerPersistence manufacturerPersistence;

    @Mock
    IRawMaterialInventoryPersistence rawMaterialInventoryPersistence;

    @Mock
    IPurchaseOrderPersistence purchaseOrderPersistence;

    @Mock
    IRawMaterialPersistence rawMaterialPersistence;

    @Mock
    IPurchaseOrderRawMaterialPersistence purchaseOrderRawMaterialPersistence;

    @Mock
    Manufacturer manufacturer;

    @Mock
    IRawMaterial rawMaterial;

    @Mock
    IRawMaterialInventory rawMaterialInventory;

    @Mock
    IPurchaseOrder purchaseOrder;

    @Mock
    PurchaseOrderRawMaterial purchaseOrderRawMaterial;

    @Mock
    PurchaseOrderFactory purchaseOrderFactory;

    @Test
    public void inventoryCheckTest() throws Exception {
        RawMaterialInventoryWatcher rawMaterialInventoryWatcher = spy(new RawMaterialInventoryWatcher());

        List<IManufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturer);

        doNothing().when(rawMaterialInventoryWatcher).inventoryCheckForEachManufacturer(anyInt());
        PowerMockito.doReturn(manufacturerPersistence).when(rawMaterialInventoryWatcher, GET_MANUFACTURER_PERSISTENCE_METHOD);
        when(manufacturerPersistence.getAll()).thenReturn(manufacturers);
        when(manufacturer.getId()).thenReturn(TEST_MANUFACTURER_ID);

        rawMaterialInventoryWatcher.inventoryCheck();

        verify(rawMaterialInventoryWatcher, times(1)).inventoryCheckForEachManufacturer(TEST_MANUFACTURER_ID);
        verify(manufacturer, times(1)).getId();
        verify(manufacturerPersistence, times(1)).getAll();

    }

    @Test
    public void inventoryCheckForEachManufacturerTest() throws Exception {
        RawMaterialInventoryWatcher rawMaterialInventoryWatcher = spy(new RawMaterialInventoryWatcher());

        List<IRawMaterial> rawMaterials = new ArrayList<>();
        rawMaterials.add(rawMaterial);
        List<IRawMaterialInventory> rawMaterialInventoryList = new ArrayList<>();
        rawMaterialInventoryList.add(rawMaterialInventory);

        doNothing().when(rawMaterialInventoryWatcher).createPurchaseOrder(anyInt(), anyList(), anyInt());
        PowerMockito.doReturn(rawMaterialPersistence).when(rawMaterialInventoryWatcher, GET_RAW_MATERIAL_PERSISTENCE_METHOD);
        when(rawMaterialPersistence.getAll(TEST_MANUFACTURER_ID)).thenReturn(rawMaterials);
        PowerMockito.doReturn(rawMaterialInventoryPersistence).when(rawMaterialInventoryWatcher, GET_RAW_MATERIAL_INVENTORY_PERSISTENCE_METHOD);
        when(rawMaterialInventoryPersistence.getAll(TEST_MANUFACTURER_ID)).thenReturn(rawMaterialInventoryList);
        when(rawMaterialInventory.getRawMaterialId()).thenReturn(TEST_RAW_MATERIAL_ID);
        when(rawMaterial.getId()).thenReturn(TEST_RAW_MATERIAL_ID);
        when(rawMaterial.getVendorId()).thenReturn(TEST_VENDOR_ID);
        when(rawMaterialInventory.getRawMaterialQuantity()).thenReturn(TEST_RAW_MATERIAL_INVENTORY_QUANTITY);
        when(rawMaterial.getReorderPointQuantity()).thenReturn(TEST_RAW_MATERIAL_REORDER_POINT);

        rawMaterialInventoryWatcher.inventoryCheckForEachManufacturer(TEST_MANUFACTURER_ID);

        verifyPrivate(rawMaterialInventoryWatcher).invoke(GET_RAW_MATERIAL_INVENTORY_PERSISTENCE_METHOD);
        verifyPrivate(rawMaterialInventoryWatcher).invoke(GET_RAW_MATERIAL_PERSISTENCE_METHOD);

        verify(rawMaterialPersistence, times(1)).getAll(anyInt());
        verify(rawMaterialInventoryPersistence, times(1)).getAll(anyInt());
        verify(rawMaterialInventoryWatcher, times(1)).createPurchaseOrder(anyInt(), anyList(), anyInt());

    }

    @Test
    public void createPurchaseOrderTest() throws Exception {
        RawMaterialInventoryWatcher rawMaterialInventoryWatcher = spy(new RawMaterialInventoryWatcher());

        List<IRawMaterial> rawMaterials = new ArrayList<>();
        rawMaterials.add(rawMaterial);

        purchaseOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        purchaseOrder.setVendorId(TEST_VENDOR_ID);

        PowerMockito.mockStatic(PurchaseOrderFactory.class);
        PowerMockito.doReturn(purchaseOrderFactory).when(PurchaseOrderFactory.class, GET_INSTANCE_METHOD);
        when(purchaseOrderFactory.makePurchaseOrder()).thenReturn(purchaseOrder);
        when(purchaseOrderFactory.makePurchaseOrderRawMaterial()).thenReturn(purchaseOrderRawMaterial);

        PowerMockito.doReturn(purchaseOrderRawMaterialPersistence).when(rawMaterialInventoryWatcher, GET_PURCHASE_ORDER_RAW_MATERIAL_PERSISTENCE_METHOD);
        PowerMockito.doReturn(purchaseOrderPersistence).when(rawMaterialInventoryWatcher, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        when(rawMaterial.getId()).thenReturn(TEST_RAW_MATERIAL_ID);
        when(rawMaterial.getName()).thenReturn(TEST_RAW_MATERIAL_NAME);
        when(rawMaterial.getReorderPointQuantity()).thenReturn(TEST_RAW_MATERIAL_REORDER_POINT);
        when(rawMaterial.getUnitMeasurementUOM()).thenReturn(TEST_RAW_MATERIAL_UNIT_MEASUREMENT_UOM);
        when(rawMaterial.getUnitCost()).thenReturn(TEST_RAW_MATERIAL_UNIT_COST);

        PowerMockito.doNothing().when(purchaseOrderRawMaterial).loadDetails(rawMaterial);
        PowerMockito.doNothing().when(purchaseOrder).addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);
        PowerMockito.doNothing().when(purchaseOrderRawMaterialPersistence).save(purchaseOrderRawMaterial);
        PowerMockito.doNothing().when(purchaseOrderPersistence).save(purchaseOrder);

        doNothing().when(rawMaterialInventoryWatcher).sendNotification(anyObject(), anyInt());

        rawMaterialInventoryWatcher.createPurchaseOrder(TEST_VENDOR_ID, rawMaterials  ,TEST_MANUFACTURER_ID);

        verify(purchaseOrderRawMaterialPersistence, times(1)).save(anyObject());
        verify(purchaseOrderPersistence, times(1)).save(anyObject());
        verify(rawMaterialInventoryWatcher, times(1)).calculatePurchaseOrderRawMaterialQuantity(anyDouble());
        verify(rawMaterialInventoryWatcher, times(1)).sendNotification(anyObject(),anyInt());

    }

    @Test
    public void calculatePurchaseOrderRawMaterialQuantityTest(){
        RawMaterialInventoryWatcher rawMaterialInventoryWatcher = spy(new RawMaterialInventoryWatcher());

        rawMaterialInventoryWatcher.calculatePurchaseOrderRawMaterialQuantity(TEST_RAW_MATERIAL_REORDER_POINT);

        verify(rawMaterialInventoryWatcher, times(1)).calculatePurchaseOrderRawMaterialQuantity(anyDouble());

    }

    @Test
    public void sendNotificationTest() throws Exception {
        RawMaterialInventoryWatcher rawMaterialInventoryWatcher = spy(new RawMaterialInventoryWatcher());

        PowerMockito.mockStatic(NotificationFactory.class);
        PowerMockito.doReturn(notificationFactory).when(NotificationFactory.class, GET_INSTANCE_METHOD);
        when(notificationFactory.makeNotification(anyInt(), anyString(), anyObject())).thenReturn(notification);
        doNothing().when(notification).send();

        rawMaterialInventoryWatcher.sendNotification(purchaseOrder, TEST_MANUFACTURER_ID);

        verify(notificationFactory, times(1)).makeNotification(anyInt(), anyString(), any());
        verify(notification, times(1)).send();

    }

}
