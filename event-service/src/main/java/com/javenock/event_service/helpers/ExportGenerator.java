package com.javenock.event_service.helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.javenock.event_service.domain.PostReport;
import com.javenock.event_service.utils.pdf.HeaderFooterPageEvent;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ExportGenerator {
    private final ExcelUtils excelUtils;

    public ExportGenerator(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    public ByteArrayOutputStream exportReportListingToPdf(List<PostReport> postReportList) throws DocumentException {
        Document document = new Document();

        document.setPageSize(PageSize.A4.rotate());
        document.setMargins(5, 5, 50, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);
        document.open();

        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_CENTER);
        Font font_a = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);

        Paragraph p_title = new Paragraph("Report Listing Report", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE, BaseColor.BLACK));
        p_title.setAlignment(Element.ALIGN_CENTER);

        Paragraph p_title_2 = null;

        Paragraph p_time = new Paragraph("Printed Date: " + LocalDateTime.now(), FontFactory.getFont(FontFactory.TIMES, 8, Font.ITALIC, BaseColor.BLACK));
        p_time.setAlignment(Element.ALIGN_RIGHT);
        document.add(p_time);
        document.add(p_title);

        PdfPTable table_PERF = new PdfPTable(6);
        table_PERF.setWidths(new int[]{2, 5, 5, 6, 5, 5});
        table_PERF.setSpacingBefore(2);
        table_PERF.setWidthPercentage(100);
        table_PERF.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 8);
        Font font3 = FontFactory.getFont(FontFactory.COURIER, 8);
        Font font_4 = FontFactory.getFont(FontFactory.HELVETICA, 7);
        Font font_5_ = FontFactory.getFont(FontFactory.HELVETICA, 7);


        table_PERF.addCell(new PdfPCell(new Phrase("No.", font)));
        table_PERF.addCell(new PdfPCell(new Phrase("title", font)));
        table_PERF.addCell(new PdfPCell(new Phrase("createdByName", font)));
        table_PERF.addCell(new PdfPCell(new Phrase("createdDate", font)));
        table_PERF.addCell(new PdfPCell(new Phrase("description", font)));
        table_PERF.addCell(new PdfPCell(new Phrase("eventType", font)));

        int i = 0;
        for (PostReport postReport : postReportList) {

            BaseColor lightGray = BaseColor.WHITE;
            String value = i + 1 + ".";
            int sumValue = i + 1;
            if (sumValue % 5 == 0) {
                lightGray = BaseColor.LIGHT_GRAY;
            }

            table_PERF.addCell(new PdfPCell(new Phrase(value, font3))).setBackgroundColor(lightGray);
            table_PERF.addCell(new PdfPCell(new Phrase(postReport.getTitle(), font3))).setBackgroundColor(lightGray);
            table_PERF.addCell(new PdfPCell(new Phrase(postReport.getCreatedByName(), font_4))).setBackgroundColor(lightGray);
            table_PERF.addCell(new PdfPCell(new Phrase(postReport.getCreatedDate().toString(), font_4))).setBackgroundColor(lightGray);
            table_PERF.addCell(new PdfPCell(new Phrase(postReport.getDescription(), font_4))).setBackgroundColor(lightGray);
            table_PERF.addCell(new PdfPCell(new Phrase(postReport.getEventType().name(), font3))).setBackgroundColor(lightGray);
            i++;
        }
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(table_PERF);
        document.close();
        return out;
    }

    public int reportListingEntriesExcel(List<PostReport> contentList, XSSFSheet sheet, int rowIndex, CellStyle stringDataCellStyleLeftAlign, CellStyle numericDataCellStyleRightAlign) {
        int colIndex;

        for (PostReport postReport : contentList) {

            colIndex = -1;
            XSSFRow row = sheet.createRow(++rowIndex);
            excelUtils.createStringDataCell(row, ++colIndex, postReport.getTitle(), stringDataCellStyleLeftAlign);
            excelUtils.createStringDataCell(row, ++colIndex, postReport.getCreatedByName(), stringDataCellStyleLeftAlign);
            excelUtils.createStringDataCell(row, ++colIndex, postReport.getCreatedDate(), stringDataCellStyleLeftAlign);
            excelUtils.createStringDataCell(row, ++colIndex, postReport.getDescription(), stringDataCellStyleLeftAlign);
            excelUtils.createStringDataCell(row, ++colIndex, postReport.getEventType(), stringDataCellStyleLeftAlign);

        }
        return rowIndex;
    }
}
