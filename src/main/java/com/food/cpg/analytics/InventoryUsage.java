package com.food.cpg.analytics;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.packaging.IPackage;
import com.food.cpg.packaging.PackageFactory;
import com.food.cpg.purchaseorder.PurchaseOrderByItem;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterial;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.salesorder.ISalesOrder;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrderFactory;

import static java.util.Calendar.DATE;

public class InventoryUsage {

    private static final int DAYS_BETWEEN_START_DATE_AND_TODAY = 7;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private String rawMaterialName;
    private double quantityForSales;
    private double quantityForCharity;
    private double totalQuantity;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date startDate;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date endDate;

    public InventoryUsage() {
        initializeInventory();
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public double getQuantityForSales() {
        return quantityForSales;
    }

    public void setQuantityForSales(double quantityForSales) {
        this.quantityForSales = quantityForSales;
    }

    public double getQuantityForCharity() {
        return quantityForCharity;
    }

    public void setQuantityForCharity(double quantityForCharity) {
        this.quantityForCharity = quantityForCharity;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void initializeInventory() {
        Date endDateValue = new Date();

        Calendar startDateValue = Calendar.getInstance();
        startDateValue.setTime(endDateValue);
        startDateValue.add(DATE, (DAYS_BETWEEN_START_DATE_AND_TODAY * -1));

        this.setStartDate(startDateValue.getTime());
        this.setEndDate(endDateValue);
    }

    public Map<String, InventoryUsage> generateInventoryUsage() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        IPackage packages = PackageFactory.instance().makePackage();
        RawMaterial rawMaterial = new RawMaterial();
        PurchaseOrderByItem purchaseOrderByItem = new PurchaseOrderByItem();
        List<ISalesOrder> salesOrders = getSalesOrderPersistence().getAllPaidOrders(loggedInManufacturerId);
        Map<String, InventoryUsage> inventoryUsages = new HashMap<>();


        for (ISalesOrder salesOrder : salesOrders) {
            if (isDateInRange(salesOrder.getStatusChangeDate())) {
                int packageid = salesOrder.getPackageId();
                packages.setPackageId(packageid);
                packages.load();
                int itemid = packages.getItemId();
                double itemquantity = packages.getQuantity();

                List<PurchaseOrderRawMaterial> itemRawMaterials = purchaseOrderByItem.getPurchaseOrderItemRawMaterial(itemid);

                for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : itemRawMaterials) {
                    int rawMaterialId = purchaseOrderRawMaterial.getRawMaterialId();
                    double rawMaterialQuantity = purchaseOrderRawMaterial.getRawMaterialQuantity();
                    double totalRawMaterialQuantity = rawMaterialQuantity * itemquantity;
                    rawMaterial.setId(rawMaterialId);
                    rawMaterial.load();
                    String rawMaterialName = rawMaterial.getName();

                    if (inventoryUsages.containsKey(rawMaterialName)) {
                        InventoryUsage inventoryUsage = inventoryUsages.get(rawMaterialName);
                        if (salesOrder.getIsForCharity()) {
                            double updatedForCharityQuantity = inventoryUsage.getQuantityForCharity() + totalRawMaterialQuantity;
                            inventoryUsage.setQuantityForCharity(updatedForCharityQuantity);
                        } else {
                            double updatedForSalesQuantity = inventoryUsage.getQuantityForSales() + totalRawMaterialQuantity;
                            inventoryUsage.setQuantityForSales(updatedForSalesQuantity);
                        }
                        inventoryUsage.setTotalQuantity(inventoryUsage.getQuantityForCharity() + inventoryUsage.getQuantityForSales());
                        inventoryUsages.replace(rawMaterialName, inventoryUsage);
                    } else {
                        InventoryUsage inventoryUsage = new InventoryUsage();
                        inventoryUsage.setRawMaterialName(rawMaterialName);
                        if (salesOrder.getIsForCharity()) {
                            inventoryUsage.setQuantityForCharity(totalRawMaterialQuantity);
                        } else {
                            inventoryUsage.setQuantityForSales(totalRawMaterialQuantity);
                        }
                        inventoryUsage.setTotalQuantity(inventoryUsage.getQuantityForCharity() + inventoryUsage.getQuantityForSales());
                        inventoryUsages.put(rawMaterialName, inventoryUsage);
                    }

                }
            }
        }
        return inventoryUsages;
    }

    public boolean isDateInRange(Date date) {
        if (date.compareTo(getStartDate()) >= 0 && date.compareTo(getEndDate()) <= 0) {
            return true;
        }
        return false;
    }

    private ISalesOrderPersistence getSalesOrderPersistence() {
        return SalesOrderFactory.instance().makeSalesOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
