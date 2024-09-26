package com.javenock.event_service.helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
public class ExcelUtils {
    private final DecimalFormat myFormatter = new DecimalFormat("#,##0.00;(#,##0.00)");

    public void createCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue((String) value);
        cell.setCellStyle(style);
    }

    public void createStringDataCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public void createNumericDataCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            try {
                Number parse = myFormatter.parse((String) value);
                cell.setCellValue(parse.doubleValue());
            } catch (Exception e) {
                cell.setCellValue((String) value);
            }
        }
        cell.setCellStyle(style);
    }

    public short getTextBuiltinFormat() {
        short textBuiltinFormat = (short) BuiltinFormats.getBuiltinFormat("@");
        return textBuiltinFormat;
    }

    public CellStyle getStringDataCellStyleCentreAlign(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font) {
        CellStyle dataCellStyleCentreAlign = getStringDataCellStyle(workbook, font, getTextBuiltinFormat());
        dataCellStyleCentreAlign.setAlignment(HorizontalAlignment.CENTER);
        return dataCellStyleCentreAlign;
    }

    public CellStyle getStringDataCellStyleLeftAlignAndTop(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font) {
        CellStyle dataCellStyleLeftAlignAndTop = getStringDataCellStyleLeftAlign(workbook, font);
        dataCellStyleLeftAlignAndTop.setVerticalAlignment(VerticalAlignment.TOP);
        dataCellStyleLeftAlignAndTop.setWrapText(true);
        return dataCellStyleLeftAlignAndTop;
    }

    public CellStyle getStringDataCellStyleRightAlign(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font) {
        CellStyle dataCellStyleRightAlign = getStringDataCellStyle(workbook, font, getTextBuiltinFormat());
        dataCellStyleRightAlign.setAlignment(HorizontalAlignment.RIGHT);
        return dataCellStyleRightAlign;
    }

    public CellStyle getNumericDataCellStyleRightAlign(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font) {
        CellStyle dataCellStyleRightAlign = getNumericDataCellStyle(workbook, font);
        dataCellStyleRightAlign.setAlignment(HorizontalAlignment.RIGHT);
        return dataCellStyleRightAlign;
    }

    public CellStyle getStringDataCellStyleLeftAlign(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font) {
        CellStyle dataCellStyleLeftAlign = getStringDataCellStyle(workbook, font, getTextBuiltinFormat());
        dataCellStyleLeftAlign.setAlignment(HorizontalAlignment.LEFT);
        return dataCellStyleLeftAlign;
    }

    public CellStyle getStringDataCellStyle(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font, short textBuiltinFormat) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(font);
        headerCellStyle.setDataFormat(textBuiltinFormat);
        return headerCellStyle;
    }

    public CellStyle getNumericDataCellStyle(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        XSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("#,##0.00_);(#,##0.00)"));
        return cellStyle;
    }

    public CellStyle getHeaderCellStyle(XSSFWorkbook workbook, org.apache.poi.ss.usermodel.Font font) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(font);
        headerCellStyle.setDataFormat(getTextBuiltinFormat());
        return headerCellStyle;
    }

    public org.apache.poi.ss.usermodel.Font getContentFont(XSSFWorkbook workbook) {
        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setFontName("Inter");
        return font;
    }

    public org.apache.poi.ss.usermodel.Font getHeaderFont(XSSFWorkbook workbook) {
        org.apache.poi.ss.usermodel.Font headerFont = getContentFont(workbook);
        headerFont.setBold(true);
        return headerFont;
    }
}
