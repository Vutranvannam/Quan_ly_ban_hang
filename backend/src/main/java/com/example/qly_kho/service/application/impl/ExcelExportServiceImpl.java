package com.example.qly_kho.service.application.impl;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.qly_kho.service.application.ExcelExportService;
import com.example.qly_kho.util.ExcelUtils;


@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    @Override
    public <T> byte[] exportTableExcel(
        List<T> data, 
        String sheetName,
        String tableTitle, 
        String[] columnHeaders,
        List<Function<T, Object>> fieldMappers
    ) {
    
        return ExcelUtils.exportTable(data, sheetName, tableTitle, columnHeaders, fieldMappers);
    }
    
}
