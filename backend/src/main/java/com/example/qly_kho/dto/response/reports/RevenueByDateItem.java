package com.example.qly_kho.dto.response.reports;

import java.math.BigDecimal;

public record RevenueByDateItem(
    String date,
    BigDecimal revenue
) {}
