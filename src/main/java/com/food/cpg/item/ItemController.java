package com.food.cpg.item;

import com.food.cpg.inventory.Unit;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.vendor.Vendor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ItemController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_ITEMS_ROUTE = "item/items";
    private static final String SHOW_ADD_ITEM_FORM_ROUTE = "item/add-item";
    private static final String VIEW_ITEMS_KEY = "items";
    private static final String VIEW_RAW_MATERIALS_KEY = "rawMaterials";
    private static final String VIEW_UNITS_KEY = "units";
    private static final String VIEW_VENDORS_KEY = "vendors";
    private static final String ITEM_ID_PATH_VARIABLE_NAME = "id";

    @GetMapping(ItemEndPoint.SHOW_ITEMS_END_POINT)
    public String showItems(Item item, Model model) {
        List<IItem> itemList = item.getAll();
        model.addAttribute(VIEW_ITEMS_KEY, itemList);
        return SHOW_ITEMS_ROUTE;
    }

    @GetMapping(ItemEndPoint.SHOW_ADD_ITEM_FORM_END_POINT)
    public String addItem(Item item, ItemRawMaterial itemRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_ITEM_FORM_ROUTE;
    }

    @PostMapping(ItemEndPoint.ADD_ITEM_RAW_MATERIAL_END_POINT)
    public String addItemRawMaterial(Item item, ItemRawMaterial itemRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        item.addItemRawMaterial(itemRawMaterial);
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_ITEM_FORM_ROUTE;
    }

    @PostMapping(ItemEndPoint.CALCULATE_TOTAL_COST_END_POINT)
    public String calculateTotalCost(Item item, ItemRawMaterial itemRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        item.calculateTotalCost();
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_ITEM_FORM_ROUTE;
    }

    @PostMapping(ItemEndPoint.SAVE_ITEM_FORM_END_POINT)
    public String saveItem(Item item) {
        item.save();
        return redirectToItems();
    }

    @GetMapping(ItemEndPoint.DELETE_ITEM_END_POINT)
    public String deleteItem(@PathVariable(ITEM_ID_PATH_VARIABLE_NAME) int itemId, Item item, ItemRawMaterial itemRawMaterial) {
        item.setId(itemId);
        itemRawMaterial.setItemId(itemId);
        item.delete();
        itemRawMaterial.delete();
        return redirectToItems();
    }

    private String redirectToItems() {
        return REDIRECT_NOTATION + ItemEndPoint.SHOW_ITEMS_END_POINT;
    }
}