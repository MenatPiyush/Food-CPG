package com.food.cpg.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RawMaterialInventoryController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String RAW_MATERIAL_INVENTORY_LIST_REQUEST_END_POINT = "/raw-materials-inventory";
    private static final String SHOW_RAW_MATERIAL_INVENTORY_ROUTE = "inventory/raw-material-inventory/raw-material-inventory";
    private static final String SHOW_RAW_MATERIAL_INVENTORY_QUANTITY_FORM_ROUTE = "inventory/raw-material-inventory/raw-material-inventory-quantity-form";
    private static final String VIEW_RAW_MATERIAL_INVENTORY_KEY = "rawMaterialInventory";
    private static final String VIEW_RAW_MATERIAL_INVENTORY_LIST_KEY = "rawMaterialInventoryList";

    @GetMapping("/raw-materials-inventory")
    public String showRawMaterialsInventory(RawMaterialInventory rawMaterialInventory, Model model) {
        model.addAttribute(VIEW_RAW_MATERIAL_INVENTORY_KEY, rawMaterialInventory.getAll());
        return SHOW_RAW_MATERIAL_INVENTORY_ROUTE;
    }

    @GetMapping("/raw-materials-inventory-quantity-form")
    public String showRawMaterialInventoryQuantityForm(RawMaterialInventory rawMaterialInventory, Model model) {
        model.addAttribute(VIEW_RAW_MATERIAL_INVENTORY_LIST_KEY, rawMaterialInventory.getAll());
        return SHOW_RAW_MATERIAL_INVENTORY_QUANTITY_FORM_ROUTE;
    }

    @PostMapping("/save-raw-materials-inventory-quantity")
    public String saveRawMaterialInventoryQuantity(RawMaterialInventory rawMaterialInventory, Model model) {
        rawMaterialInventory.increaseQuantity();
        return redirectToRawMaterialInventory();
    }

    private String redirectToRawMaterialInventory() {
        return REDIRECT_NOTATION + RAW_MATERIAL_INVENTORY_LIST_REQUEST_END_POINT;
    }
}