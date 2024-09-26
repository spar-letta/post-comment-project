package com.javenock.event_service.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.itextpdf.text.DocumentException;
import com.javenock.event_service.domain.PostReport;
import com.javenock.event_service.domain.dataTypes.ExportType;
import com.javenock.event_service.service.PostReportService;
import com.javenock.event_service.views.BaseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@RequestMapping("/reportSummary")
@RestController
@RequiredArgsConstructor
public class ReportSummaryController {

    private final PostReportService postReportService;

    @PreAuthorize("hasAuthority('READ_REPORT')")
    @GetMapping
    @JsonView(BaseView.SummaryView.class)
    @PageableAsQueryParam
    public Page<PostReport> reportSummary(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postReportService.fetchAll(pageable);
    }

    @GetMapping(value = "/export")
    @Operation(summary = "post listing statement", responses = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity exportMemberListingStatement(
            @RequestParam(value = "exportType") ExportType exportType,
            @RequestParam(required = false, name = "searchParam") String searchParam,
            @Parameter(hidden = true) @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Parameter(hidden = true) Authentication loggedInUserDetails) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = postReportService.exportReportListings(searchParam, exportType, loggedInUserDetails, pageable);
        String contentDisposition = String.format("attachment; filename=\"Insurer Statements - %s.%s\"", LocalDateTime.now().toString(), exportType.extension);
        String mediaType = exportType.contentType;
        return ResponseEntity.ok().contentLength(byteArrayOutputStream.size())
                .contentType(MediaType.parseMediaType(mediaType))
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", contentDisposition)
                .body(byteArrayOutputStream.toByteArray());
    }
}
