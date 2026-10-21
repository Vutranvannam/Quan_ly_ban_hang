package com.example.qly_kho.dto.response.reports;

import java.math.BigDecimal;

public record TopProductItem(
    String name,
    BigDecimal quantity
) {}
