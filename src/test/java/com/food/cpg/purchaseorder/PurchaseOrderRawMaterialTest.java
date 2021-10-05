package com.food.cpg.purchaseorder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.rawmaterial.IRawMaterial;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PurchaseOrderRawMaterial.class)
public class PurchaseOrderRawMaterialTest {

    private static final double DELTA = 1e-15;
    private static final int TEST_RAW_MATERIAL_ID = 1;
    private static final String TEST_RAW_MATERIAL_NAME = "Test Raw Material 1";
    private static final double TEST_RAW_MATERIAL_COST = 20.0;
    private static final double TEST_RAW_MATERIAL_QUANTITY = 10.0;
    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    IPurchaseOrderRawMaterialPersistence purchaseOrderRawMaterialPersistence;

    @Mock
    IRawMaterial rawMaterial;

    @Test
    public void saveTest() throws Exception {
        IPurchaseOrderRawMaterial purchaseOrderRawMaterial = spy(new PurchaseOrderRawMaterial());

        PowerMockito.doReturn(purchaseOrderRawMaterialPersistence).when(purchaseOrderRawMaterial, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderRawMaterialPersistence).save(purchaseOrderRawMaterial);

        purchaseOrderRawMaterial.save();
        verifyPrivate(purchaseOrderRawMaterial).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(purchaseOrderRawMaterialPersistence, times(1)).save(purchaseOrderRawMaterial);
    }

    @Test
    public void loadDetailsTest() {
        IPurchaseOrderRawMaterial purchaseOrderRawMaterial = new PurchaseOrderRawMaterial();
        purchaseOrderRawMaterial.setRawMaterialId(TEST_RAW_MATERIAL_ID);
        purchaseOrderRawMaterial.setRawMaterialQuantity(TEST_RAW_MATERIAL_QUANTITY);

        when(rawMaterial.getUnitCost()).thenReturn(TEST_RAW_MATERIAL_COST);
        when(rawMaterial.getName()).thenReturn(TEST_RAW_MATERIAL_NAME);

        purchaseOrderRawMaterial.loadDetails(rawMaterial);

        Assert.assertEquals(200.0, purchaseOrderRawMaterial.getRawMaterialCost(), DELTA);
    }
}
