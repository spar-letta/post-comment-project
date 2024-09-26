package com.javenock.event_service.service;

import com.itextpdf.text.DocumentException;
import com.javenock.event_service.domain.PostReport;
import com.javenock.event_service.domain.dataTypes.ExportType;
import com.javenock.event_service.domain.dto.PostEventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayOutputStream;

public interface PostReportService {
    void createPostReport(PostEventDto evt);

    Page<PostReport> fetchAll(Pageable pageable);

    ByteArrayOutputStream exportReportListings(String searchParam, ExportType exportType, Authentication loggedInUserDetails, Pageable pageable) throws Exception;
}
