package com.food.cpg.inventory;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.food.cpg.item.Item;

@Controller
public class ItemInventoryController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_ITEM_INVENTORY_ROUTE = "inventory/item-inventory/item-inventory";
    private static final String ITEM_INVENTORY_LIST_REQUEST_END_POINT = "/items-inventory";
    private static final String VIEW_ITEM_INVENTORY_KEY = "itemInventory";
    private static final String VIEW_ITEM_INVENTORY_LIST_KEY = "itemInventoryList";
    private static final String VIEW_ITEM_KEY = "item";
    private static final String SHOW_ITEM_INVENTORY_QUANTITY_FORM_ROUTE = "inventory/item-inventory/item-inventory-quantity-form";

    @GetMapping("/items-inventory")
    public String showItemsInventory(ItemInventory itemInventory, Model model) {
        List<ItemInventory> itemInventoryList = itemInventory.getAll();
        model.addAttribute(VIEW_ITEM_INVENTORY_KEY, itemInventoryList);
        return SHOW_ITEM_INVENTORY_ROUTE;
    }

    @GetMapping("/items-inventory-quantity-form")
    public String showItemInventoryQuantityForm(ItemInventory itemInventory, Item item, Model model) {
        List<ItemInventory> itemInventoryList = itemInventory.getAll();
        model.addAttribute(VIEW_ITEM_INVENTORY_LIST_KEY, itemInventoryList);
        model.addAttribute(VIEW_ITEM_KEY, item.getAll());
        return SHOW_ITEM_INVENTORY_QUANTITY_FORM_ROUTE;
    }

    @PostMapping("/save-items-inventory-quantity")
    public String saveItemInventoryQuantity(ItemInventory itemInventory, Model model) {
        itemInventory.increaseQuantity();
        return redirectToItemInventory();
    }

    private String redirectToItemInventory() {
        return REDIRECT_NOTATION + ITEM_INVENTORY_LIST_REQUEST_END_POINT;
    }
}