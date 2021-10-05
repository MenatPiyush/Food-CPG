package com.food.cpg.purchaseorder;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseOrderRawMaterialDatabasePersistenceTest {

    private static final String TEST_PURCHASE_ORDER_NUMBER = "PO-123";
    private static final Integer TEST_PO_RAW_MATERIAL_ID = 1;
    private static final Double TEST_PO_RAW_MATERIAL_QUANTITY = 10.0;
    private static final String TEST_PO_RAW_MATERIAL_UOM = "g";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    IPurchaseOrderRawMaterial purchaseOrderRawMaterial;

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(purchaseOrderRawMaterial.getPurchaseOrderNumber()).thenReturn(TEST_PURCHASE_ORDER_NUMBER);
        when(purchaseOrderRawMaterial.getRawMaterialId()).thenReturn(TEST_PO_RAW_MATERIAL_ID);
        when(purchaseOrderRawMaterial.getRawMaterialQuantity()).thenReturn(TEST_PO_RAW_MATERIAL_QUANTITY);
        when(purchaseOrderRawMaterial.getRawMaterialQuantityUOM()).thenReturn(TEST_PO_RAW_MATERIAL_UOM);

        PurchaseOrderRawMaterialDatabasePersistence purchaseOrderDatabasePersistence = new PurchaseOrderRawMaterialDatabasePersistence(commonDatabaseOperation);

        purchaseOrderDatabasePersistence.save(purchaseOrderRawMaterial);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(purchaseOrderRawMaterial, times(1)).getPurchaseOrderNumber();
        verify(purchaseOrderRawMaterial, times(1)).getRawMaterialId();
        verify(purchaseOrderRawMaterial, times(1)).getRawMaterialQuantity();
        verify(purchaseOrderRawMaterial, times(1)).getRawMaterialQuantityUOM();
    }
}
