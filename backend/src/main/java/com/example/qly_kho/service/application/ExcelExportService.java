package com.example.qly_kho.service.application;

import java.util.List;
import java.util.function.Function;

public interface ExcelExportService {
    <T> byte[] exportTableExcel(List<T> data, String sheetName, String tableTitle, String[] columnHeaders, List<Function<T, Object>> fieldMappers);
}
