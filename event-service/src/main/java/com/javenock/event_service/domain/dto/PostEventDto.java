package com.javenock.event_service.domain.dto;

import com.javenock.event_service.domain.dataTypes.EventType;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostEventDto{
    private UUID publicId;
    private String title;
    private String createdByName;
    private String description;
    private EventType eventType;
}
