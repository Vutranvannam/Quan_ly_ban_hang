package com.example.qly_kho.dto.response.reports;

import java.math.BigDecimal;

public record InventorySummaryItem(
    String name,
    BigDecimal quantity
) {}
