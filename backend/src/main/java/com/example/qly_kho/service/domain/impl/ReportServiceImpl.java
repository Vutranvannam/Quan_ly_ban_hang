package com.example.qly_kho.service.domain.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.qly_kho.dto.response.reports.InventorySummaryItem;
import com.example.qly_kho.dto.response.reports.ReportsResponse;
import com.example.qly_kho.dto.response.reports.RevenueByDateItem;
import com.example.qly_kho.dto.response.reports.TopProductItem;
import com.example.qly_kho.repository.ReportQueryRepository;
import com.example.qly_kho.service.ReportService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private static final int TOP_PRODUCTS_LIMIT = 5;
    private static final int INVENTORY_SUMMARY_LIMIT = 10;

    private final ReportQueryRepository reportQueryRepository;

    @Override
    public ReportsResponse getReports(LocalDate fromDate, LocalDate toDate) {
        Object[] overview = reportQueryRepository.getOverview(fromDate, toDate);

        List<RevenueByDateItem> revenueByDate = reportQueryRepository.getRevenueByDate(fromDate, toDate)
            .stream()
            .map(row -> new RevenueByDateItem(
                row[0] != null ? row[0].toString() : null,
                toBigDecimal(row[1])
            ))
            .toList();

        List<TopProductItem> topProducts = reportQueryRepository.getTopProducts(fromDate, toDate, TOP_PRODUCTS_LIMIT)
            .stream()
            .map(row -> new TopProductItem(
                row[0] != null ? row[0].toString() : "",
                toBigDecimal(row[1])
            ))
            .toList();

        List<InventorySummaryItem> inventorySummary = reportQueryRepository.getInventorySummary(INVENTORY_SUMMARY_LIMIT)
            .stream()
            .map(row -> new InventorySummaryItem(
                row[0] != null ? row[0].toString() : "",
                toBigDecimal(row[1])
            ))
            .toList();

        return new ReportsResponse(
            toBigDecimal(overview[0]),
            toLong(overview[1]),
            toBigDecimal(overview[2]),
            toBigDecimal(overview[3]),
            revenueByDate,
            topProducts,
            inventorySummary
        );
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal decimal) {
            return decimal;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return new BigDecimal(value.toString());
    }

    private Long toLong(Object value) {
        if (value == null) {
            return 0L;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(value.toString());
    }
}
