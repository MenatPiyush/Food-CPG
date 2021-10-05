package com.food.cpg.rawmaterial;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.food.cpg.inventory.Unit;
import com.food.cpg.vendor.Vendor;

@Controller
public class RawMaterialController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_RAW_MATERIALS_ROUTE = "raw-material/raw-materials";
    private static final String SHOW_ADD_RAW_MATERIAL_FORM_ROUTE = "raw-material/add-raw-material";
    private static final String SHOW_EDIT_RAW_MATERIAL_FORM_ROUTE = "raw-material/edit-raw-material";
    private static final String VIEW_RAW_MATERIAL_KEY = "rawMaterial";
    private static final String VIEW_RAW_MATERIALS_KEY = "rawMaterials";
    private static final String VIEW_UNITS_KEY = "units";
    private static final String VIEW_VENDORS_KEY = "vendors";
    private static final String RAW_MATERIAL_ID_PATH_VARIABLE_NAME = "rawMaterialId";

    @GetMapping(RawMaterialEndpoint.RAW_MATERIALS_END_POINT)
    public String showRawMaterials(RawMaterial rawMaterial, Model model) {
        List<IRawMaterial> rawMaterials = rawMaterial.getAll();
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterials);
        return SHOW_RAW_MATERIALS_ROUTE;
    }

    @GetMapping(RawMaterialEndpoint.ADD_RAW_MATERIALS_END_POINT)
    public String showAddRawMaterialForm(RawMaterial rawMaterial, Vendor vendor, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        return SHOW_ADD_RAW_MATERIAL_FORM_ROUTE;
    }

    @PostMapping(RawMaterialEndpoint.SAVE_RAW_MATERIALS_END_POINT)
    public String saveRawMaterial(RawMaterial rawMaterial, Vendor vendor, BindingResult result, Model model) {
        if (result.hasErrors() || !rawMaterial.isValidRawMaterial()) {
            model.addAttribute(VIEW_UNITS_KEY, Unit.values());
            model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
            return SHOW_ADD_RAW_MATERIAL_FORM_ROUTE;
        }

        rawMaterial.save();
        return redirectToRawMaterialList();
    }

    @PostMapping(RawMaterialEndpoint.EDIT_RAW_MATERIALS_END_POINT)
    public String editRawMaterial(RawMaterial rawMaterial, BindingResult result) {
        if (result.hasErrors() || !rawMaterial.isValidRawMaterial()) {
            return SHOW_EDIT_RAW_MATERIAL_FORM_ROUTE;
        }

        rawMaterial.update();
        return redirectToRawMaterialList();
    }

    @GetMapping(RawMaterialEndpoint.EDIT_RAW_MATERIALS_FORM_END_POINT)
    public String showEditRawMaterialForm(@PathVariable(RAW_MATERIAL_ID_PATH_VARIABLE_NAME) int rawMaterialId, RawMaterial rawMaterial, Vendor vendor, Model model) {
        rawMaterial.setId(rawMaterialId);
        rawMaterial.load();

        model.addAttribute(VIEW_RAW_MATERIAL_KEY, rawMaterial);
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        return SHOW_EDIT_RAW_MATERIAL_FORM_ROUTE;
    }

    @GetMapping(RawMaterialEndpoint.DELETE_RAW_MATERIALS_END_POINT)
    public String deleteRawMaterial(@PathVariable(RAW_MATERIAL_ID_PATH_VARIABLE_NAME) int rawMaterialId, RawMaterial rawMaterial) {
        rawMaterial.setId(rawMaterialId);
        rawMaterial.delete();

        return redirectToRawMaterialList();
    }

    private String redirectToRawMaterialList() {
        return REDIRECT_NOTATION + RawMaterialEndpoint.RAW_MATERIALS_END_POINT;
    }
}