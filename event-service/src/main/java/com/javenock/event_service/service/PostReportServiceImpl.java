package com.javenock.event_service.service;

import com.itextpdf.text.DocumentException;
import com.javenock.event_service.domain.PostReport;
import com.javenock.event_service.domain.dataTypes.ExportType;
import com.javenock.event_service.domain.dto.PostEventDto;
import com.javenock.event_service.helpers.ExcelUtils;
import com.javenock.event_service.helpers.ExportGenerator;
import com.javenock.event_service.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostReportServiceImpl implements PostReportService {

    private final PostReportRepository postReportRepository;

    private final ExportGenerator exportGenerator;
    private final ExcelUtils excelUtils;

    @Override
    public void createPostReport(PostEventDto evt) {
        PostReport report = PostReport.builder()
                .publicId(evt.getPublicId())
                .title(evt.getTitle())
                .createdByName(evt.getCreatedByName())
                .createdDate(LocalDateTime.now())
                .description(evt.getDescription())
                .eventType(evt.getEventType())
                .build();
        postReportRepository.save(report);
    }

    @Override
    public Page<PostReport> fetchAll(Pageable pageable) {
        return postReportRepository.findAll(pageable);
    }

    @Override
    public ByteArrayOutputStream exportReportListings(String searchParam, ExportType exportType, Authentication loggedInUserDetails, Pageable pageable) throws Exception {
        List<PostReport> contentList = fetchAll(pageable).getContent();
        if (exportType == ExportType.PDF) {
            return exportGenerator.exportReportListingToPdf(contentList);
        } else {
            return exportReportListingToExcel(contentList, loggedInUserDetails);
        }
    }

    private ByteArrayOutputStream exportReportListingToExcel(List<PostReport> contentList, Authentication loggedInUser) throws Exception {

        try {
            String documentTitle = "Report Listing Report";
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(documentTitle);

            org.apache.poi.ss.usermodel.Font headerFont = excelUtils.getHeaderFont(workbook);

            org.apache.poi.ss.usermodel.Font font = excelUtils.getContentFont(workbook);

            CellStyle headerCellStyle = excelUtils.getHeaderCellStyle(workbook, headerFont);

            CellStyle headerCellStyleCentreAlign = excelUtils.getStringDataCellStyleCentreAlign(workbook, headerFont);

            CellStyle stringDataCellStyleLeftAlign = excelUtils.getStringDataCellStyleLeftAlign(workbook, font);

            CellStyle numericDataCellStyleRightAlign = excelUtils.getNumericDataCellStyleRightAlign(workbook, font);

            CellStyle stringDataCellStyleCentreAlign = excelUtils.getStringDataCellStyleCentreAlign(workbook, font);

            int rowIndex = 1;

            XSSFRow row1 = sheet.createRow(++rowIndex);
            excelUtils.createStringDataCell(row1, 0, documentTitle, headerCellStyleCentreAlign);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 20));

            XSSFRow row3 = sheet.createRow(++rowIndex);
            excelUtils.createStringDataCell(row3, 0, "Printed By: \t \t     " + loggedInUser.getName(), stringDataCellStyleCentreAlign);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 20));

            XSSFRow row4 = sheet.createRow(++rowIndex);
            excelUtils.createStringDataCell(row4, 0, "Printed On: \t \t     " + LocalDateTime.now().toString(), stringDataCellStyleCentreAlign);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 20));

            List<String> contentHeaders = Arrays.asList(
                    "Title",
                    "Created By Name",
                    "Created Date",
                    "Description",
                    "Event Type"
            );

            int colIndex;
            Row headerRow = sheet.createRow(rowIndex += 2);

            for (int i = 0; i < contentHeaders.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(contentHeaders.get(i));
                cell.setCellStyle(headerCellStyle);
            }

            ++rowIndex;

            exportGenerator.reportListingEntriesExcel(contentList, sheet, rowIndex, stringDataCellStyleLeftAlign, numericDataCellStyleRightAlign);

            for (int i = 0; i < contentHeaders.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();

            return bos;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new Exception("Sorry. Error in create file");
        }
    }
}
