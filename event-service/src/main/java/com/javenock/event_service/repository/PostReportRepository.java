package com.javenock.event_service.repository;

import com.javenock.event_service.domain.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
}
