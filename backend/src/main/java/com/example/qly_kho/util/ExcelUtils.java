package com.example.qly_kho.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class ExcelUtils {

    public static <T> byte[] exportTable(
            List<T> data,
            String sheetName,
            String tableTitle,
            String[] columnHeaders,
            List<Function<T, Object>> fieldMappers
    ) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetName);

            int colSize = columnHeaders.length;

            // =========================
            // STYLE
            // =========================
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle bodyStyle = createBodyStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);

            // =========================
            // TITLE ROW (MERGE)
            // =========================
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(tableTitle);
            titleCell.setCellStyle(titleStyle);

            sheet.addMergedRegion(new CellRangeAddress(
                    0, 0, 0, colSize - 1
            ));

            // =========================
            // HEADER ROW
            // =========================
            Row headerRow = sheet.createRow(1);

            for (int i = 0; i < colSize; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);
                cell.setCellStyle(headerStyle);
            }

            // =========================
            // DATA
            // =========================
            int rowIdx = 2;

            for (T item : data) {
                Row row = sheet.createRow(rowIdx++);

                for (int i = 0; i < fieldMappers.size(); i++) {
                    Cell cell = row.createCell(i);

                    Object value = fieldMappers.get(i).apply(item);

                    setCellValue(cell, value, bodyStyle, dateStyle);
                }
            }

            // =========================
            // AUTO SIZE
            // =========================
            for (int i = 0; i < colSize; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error exporting Excel", e);
        }
    }

    // =========================
    // TITLE STYLE
    // =========================
    private static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    // =========================
    // HEADER STYLE
    // =========================
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        setBorder(style);

        return style;
    }

    // =========================
    // BODY STYLE
    // =========================
    private static CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(style);

        return style;
    }

    // =========================
    // DATE STYLE
    // =========================
    private static CellStyle createDateStyle(Workbook workbook) {
        CreationHelper createHelper = workbook.getCreationHelper();

        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy")
        );

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(style);

        return style;
    }

    // =========================
    // BORDER (QUAN TRỌNG)
    // =========================
    private static void setBorder(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    // =========================
    // SET VALUE
    // =========================
    private static void setCellValue(
            Cell cell,
            Object value,
            CellStyle bodyStyle,
            CellStyle dateStyle
    ) {

        cell.setCellStyle(bodyStyle);

        if (value == null) {
            cell.setCellValue("");

        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());

        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);

        } else if (value instanceof LocalDateTime) {
            cell.setCellStyle(dateStyle);
            cell.setCellValue((LocalDateTime) value);

        } else {
            cell.setCellValue(value.toString());
        }
    }
}