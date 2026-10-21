package com.example.qly_kho.service;

import java.time.LocalDate;

import com.example.qly_kho.dto.response.reports.ReportsResponse;

public interface ReportService {
    ReportsResponse getReports(LocalDate fromDate, LocalDate toDate);
}
