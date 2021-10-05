package com.food.cpg.analytics;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnalyticsController {

    private static final String ANALYTICS_ROUTE = "analytics/analytics";
    private static final String LOAD_BALANCE_SHEET_ROUTE = "analytics/balance-sheet";
    private static final String LOAD_ORDER_STATISTICS_ROUTE = "analytics/order-statistics";
    private static final String LOAD_INVENTORY_USAGE_ROUTE = "analytics/inventory-usage";
    private static final String VIEW_INVENTORY_USAGE_KEY = "inventoryUsages";
    private static final String LOAD_SALES_PERFORMANCE_ROUTE = "analytics/sales-performance";
    private static final String VIEW_SALES_PERFORMANCE_KEY = "salesPerformances";

    @GetMapping(AnalyticsEndpoint.ANALYTICS_ROOT_END_POINT)
    public String showAnalytics() {
        return ANALYTICS_ROUTE;
    }

    @RequestMapping(AnalyticsEndpoint.LOAD_BALANCE_SHEET_END_POINT)
    public String loadBalanceSheet(BalanceSheet balanceSheet) {
        balanceSheet.generateBalanceSheet();

        return LOAD_BALANCE_SHEET_ROUTE;
    }

    @RequestMapping(AnalyticsEndpoint.LOAD_ORDER_STATISTICS_END_POINT)
    public String loadOrderStatistics(OrderStatistics orderStatistics) {
        orderStatistics.generateOrderStatistics();

        return LOAD_ORDER_STATISTICS_ROUTE;
    }

    @RequestMapping(AnalyticsEndpoint.LOAD_INVENTORY_USAGE_END_POINT)
    public String loadInventoryUsage(InventoryUsage inventoryUsage, Model model) {
        model.addAttribute(VIEW_INVENTORY_USAGE_KEY, inventoryUsage.generateInventoryUsage());

        return LOAD_INVENTORY_USAGE_ROUTE;
    }

    @RequestMapping(AnalyticsEndpoint.LOAD_SALES_PERFORMANCE_END_POINT)
    public String loadSalesPerformance(SalesPerformance salesPerformance, Model model) {
        model.addAttribute(VIEW_SALES_PERFORMANCE_KEY, salesPerformance.generateSalesPerformance());

        return LOAD_SALES_PERFORMANCE_ROUTE;
    }
}