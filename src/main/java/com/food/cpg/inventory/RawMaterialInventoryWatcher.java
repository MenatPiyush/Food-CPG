package com.food.cpg.inventory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.ManufacturerFactory;
import com.food.cpg.notification.INotification;
import com.food.cpg.notification.NotificationFactory;
import com.food.cpg.purchaseorder.IPurchaseOrder;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.IPurchaseOrderRawMaterial;
import com.food.cpg.purchaseorder.IPurchaseOrderRawMaterialPersistence;
import com.food.cpg.purchaseorder.PurchaseOrderFactory;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterial;
import com.food.cpg.rawmaterial.IRawMaterial;
import com.food.cpg.rawmaterial.IRawMaterialPersistence;

@Component
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
public class RawMaterialInventoryWatcher {

    private static final String NEW_PURCHASE_ORDER_CREATION_MESSAGE = "Raw Material inventory for raw material(s) %s was low from vendor %s. A new purchase order- %s amounting- %s has been created.";
    private static final String COMMA = ",";

    @Scheduled(initialDelay = 3000000, fixedDelay = 3000000)
    public void inventoryCheck() {
        List<IManufacturer> manufacturers = getManufacturerPersistence().getAll();

        for (IManufacturer manufacturer : manufacturers) {
            inventoryCheckForEachManufacturer(manufacturer.getId());
        }
    }

    public void inventoryCheckForEachManufacturer(int manufacturerId) {
        List<IRawMaterial> rawMaterials = getRawMaterialPersistence().getAll(manufacturerId);
        List<IRawMaterialInventory> rawMaterialInventoryList = getRawMaterialInventoryPersistence().getAll(manufacturerId);

        Map<Integer, IRawMaterialInventory> rawMaterialInventoryMap = new HashMap<>();
        for (IRawMaterialInventory rawMaterialInventory : rawMaterialInventoryList) {
            rawMaterialInventoryMap.put(rawMaterialInventory.getRawMaterialId(), rawMaterialInventory);
        }

        Map<Integer, List<IRawMaterial>> rawMaterialMap = new HashMap<>();
        if (rawMaterialInventoryList.size() > 0) {
            for (IRawMaterial rawMaterial : rawMaterials) {
                IRawMaterialInventory rawMaterialInventory = rawMaterialInventoryMap.get(rawMaterial.getId());

                if (rawMaterialInventory.getRawMaterialQuantity() < rawMaterial.getReorderPointQuantity()) {
                    if (rawMaterialMap.containsKey(rawMaterial.getVendorId())) {
                        List<IRawMaterial> rawMaterialList = rawMaterialMap.get(rawMaterial.getVendorId());
                        rawMaterialList.add(rawMaterial);
                        rawMaterialMap.put(rawMaterial.getVendorId(), rawMaterialList);
                    } else {
                        List<IRawMaterial> rawMaterialList = new ArrayList<>();
                        rawMaterialList.add(rawMaterial);
                        rawMaterialMap.put(rawMaterial.getVendorId(), rawMaterialList);
                    }
                }
            }
        }

        for (Map.Entry<Integer, List<IRawMaterial>> entry : rawMaterialMap.entrySet()) {
            createPurchaseOrder(entry.getKey(), entry.getValue(), manufacturerId);
        }
    }

    public void createPurchaseOrder(Integer vendorId, List<IRawMaterial> rawMaterials, int manufacturerId) {
        IPurchaseOrder purchaseOrder = PurchaseOrderFactory.instance().makePurchaseOrder();

        purchaseOrder.setManufacturerId(manufacturerId);
        purchaseOrder.setVendorId(vendorId);

        for (IRawMaterial rawMaterial : rawMaterials) {
            PurchaseOrderRawMaterial purchaseOrderRawMaterial = PurchaseOrderFactory.instance().makePurchaseOrderRawMaterial();
            purchaseOrderRawMaterial.setRawMaterialId(rawMaterial.getId());
            purchaseOrderRawMaterial.setRawMaterialName(rawMaterial.getName());
            purchaseOrderRawMaterial.setRawMaterialQuantity(calculatePurchaseOrderRawMaterialQuantity(rawMaterial.getReorderPointQuantity()));
            purchaseOrderRawMaterial.setRawMaterialQuantityUOM(rawMaterial.getUnitMeasurementUOM());
            purchaseOrderRawMaterial.setRawMaterialCost(rawMaterial.getUnitCost());
            purchaseOrderRawMaterial.loadDetails(rawMaterial);
            purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);
            getPurchaseOrderRawMaterialPersistence().save(purchaseOrderRawMaterial);
        }

        getPurchaseOrderPersistence().save(purchaseOrder);
        sendNotification(purchaseOrder, manufacturerId);
    }

    public double calculatePurchaseOrderRawMaterialQuantity(Double reOrderPoint) {
        Double rawMaterialQuantity = reOrderPoint * 5;
        return rawMaterialQuantity;
    }

    public void sendNotification(IPurchaseOrder purchaseOrder, int manufacturerId) {

        StringJoiner rawMaterialNameJoiner = new StringJoiner(COMMA);

        for (IPurchaseOrderRawMaterial purchaseOrderRawMaterial : purchaseOrder.getPurchaseOrderRawMaterials()) {
            rawMaterialNameJoiner.add(purchaseOrderRawMaterial.getRawMaterialName());
        }

        String notificationContent = String.format(NEW_PURCHASE_ORDER_CREATION_MESSAGE, rawMaterialNameJoiner.toString(), purchaseOrder.getVendorId(), purchaseOrder.getOrderNumber(), purchaseOrder.getTotalCost());
        INotification notification = NotificationFactory.instance().makeNotification(manufacturerId, notificationContent, Timestamp.from(Instant.now()));
        notification.send();
    }

    private IRawMaterialInventoryPersistence getRawMaterialInventoryPersistence() {
        return InventoryFactory.instance().makeRawMaterialInventoryPersistence();
    }

    private IManufacturerPersistence getManufacturerPersistence() {
        return ManufacturerFactory.instance().makeManufacturerPersistence();
    }

    private IRawMaterialPersistence getRawMaterialPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRawMaterialPersistence();
    }

    private IPurchaseOrderPersistence getPurchaseOrderPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderPersistence();
    }

    private IPurchaseOrderRawMaterialPersistence getPurchaseOrderRawMaterialPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderRawMaterialPersistence();
    }
}
