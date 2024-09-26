package com.javenock.project.dto;

import com.javenock.project.model.dataType.EventType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PostEventDto {
    private UUID publicId;
    private String title;
    private String createdByName;
    private String description;
    private EventType eventType;
}


