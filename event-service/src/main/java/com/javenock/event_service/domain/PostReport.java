package com.javenock.event_service.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.event_service.domain.dataTypes.EventType;
import com.javenock.event_service.views.BaseView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "post_report", schema = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "public_id")
    @JsonView(BaseView.SummaryView.class)
    private UUID publicId;

    @Column(name = "title")
    @JsonView(BaseView.SummaryView.class)
    private String title;

    @Column(name = "creator_name")
    @JsonView(BaseView.SummaryView.class)
    private String createdByName;

    @Column(name = "created_date")
    @JsonView(BaseView.SummaryView.class)
    private LocalDateTime createdDate;

    @Column(name = "description")
    @JsonView(BaseView.SummaryView.class)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    @JsonView(BaseView.SummaryView.class)
    private EventType eventType;
}
