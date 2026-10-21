package com.example.qly_kho.dto.response.reports;

import java.math.BigDecimal;
import java.util.List;

public record ReportsResponse(
    BigDecimal revenue,
    Long totalOrders,
    BigDecimal importCost,
    BigDecimal inventoryValue,
    List<RevenueByDateItem> revenueByDate,
    List<TopProductItem> topProducts,
    List<InventorySummaryItem> inventorySummary
) {}
