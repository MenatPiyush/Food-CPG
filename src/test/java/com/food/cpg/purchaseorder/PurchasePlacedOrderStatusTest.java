package com.food.cpg.purchaseorder;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PurchasePlacedOrderStatus.class, InventoryFactory.class})
public class PurchasePlacedOrderStatusTest {

    private static final String GET_INSTANCE_METHOD = "instance";
    private static final String TEST_PURCHASE_ORDER_NUMBER = "PO-1234";
    private static final Integer TEST_RAW_MATERIAL_ID = 1;
    private static final String TEST_RAW_MATERIAL_UOM = "g";
    private static final Double TEST_RAW_MATERIAL_QUANTITY = 10.0;
    private static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final String GET_PO_RAW_MATERIAL_PERSISTENCE_METHOD = "getPurchaseOrderRawMaterialPersistence";

    @Mock
    IPurchaseOrderPersistence purchaseOrderPersistence;

    @Mock
    IPurchaseOrderRawMaterialPersistence purchaseOrderRawMaterialPersistence;

    @Mock
    InventoryFactory inventoryFactory;

    @Mock
    IRawMaterialInventory rawMaterialInventory;

    @Mock
    IPurchaseOrder purchaseOrder;

    @Mock
    IPurchaseOrderRawMaterial purchaseOrderRawMaterial;

    @Test
    public void getOrderStatusTest() {
        PurchasePlacedOrderStatus purchasePlacedOrderStatus = new PurchasePlacedOrderStatus();

        Assert.assertEquals(PurchaseOrderStatus.Status.PLACED, purchasePlacedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        PurchasePlacedOrderStatus purchasePlacedOrderStatus = spy(new PurchasePlacedOrderStatus());

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchasePlacedOrderStatus, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(purchaseOrderPersistence).changeStatus(anyString(), anyString());
        PowerMockito.doNothing().when(purchasePlacedOrderStatus).increaseRawMaterialQuantity(any());
        PowerMockito.doReturn(TEST_PURCHASE_ORDER_NUMBER).when(purchaseOrder).getOrderNumber();

        purchasePlacedOrderStatus.moveOrder(purchaseOrder);

        verify(purchaseOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }

    @Test
    public void increaseRawMaterialQuantityTest() throws Exception {
        PurchasePlacedOrderStatus purchasePlacedOrderStatus = spy(new PurchasePlacedOrderStatus());

        List<IPurchaseOrderRawMaterial> purchaseOrderRawMaterials = new ArrayList<>();
        purchaseOrderRawMaterials.add(purchaseOrderRawMaterial);

        PowerMockito.mockStatic(InventoryFactory.class);
        PowerMockito.doReturn(inventoryFactory).when(InventoryFactory.class, GET_INSTANCE_METHOD);
        when(inventoryFactory.makeRawMaterialInventory()).thenReturn(rawMaterialInventory);
        doNothing().when(rawMaterialInventory).increaseQuantity();

        PowerMockito.doReturn(purchaseOrderRawMaterialPersistence).when(purchasePlacedOrderStatus, GET_PO_RAW_MATERIAL_PERSISTENCE_METHOD);
        PowerMockito.doReturn(purchaseOrderRawMaterials).when(purchaseOrderRawMaterialPersistence).getPurchaseOrderRawMaterials(anyString());
        PowerMockito.doReturn(purchaseOrderRawMaterials).when(purchaseOrder).getPurchaseOrderRawMaterials();
        PowerMockito.doReturn(TEST_PURCHASE_ORDER_NUMBER).when(purchaseOrder).getOrderNumber();
        PowerMockito.doReturn(TEST_RAW_MATERIAL_ID).when(purchaseOrderRawMaterial).getRawMaterialId();
        PowerMockito.doReturn(TEST_RAW_MATERIAL_QUANTITY).when(purchaseOrderRawMaterial).getRawMaterialQuantity();
        PowerMockito.doReturn(TEST_RAW_MATERIAL_UOM).when(purchaseOrderRawMaterial).getRawMaterialQuantityUOM();

        purchasePlacedOrderStatus.increaseRawMaterialQuantity(purchaseOrder);

        verifyPrivate(purchasePlacedOrderStatus).invoke(GET_PO_RAW_MATERIAL_PERSISTENCE_METHOD);
        verify(purchaseOrderRawMaterialPersistence, times(1)).getPurchaseOrderRawMaterials(TEST_PURCHASE_ORDER_NUMBER);
        verify(rawMaterialInventory, times(1)).increaseQuantity();
    }
}
