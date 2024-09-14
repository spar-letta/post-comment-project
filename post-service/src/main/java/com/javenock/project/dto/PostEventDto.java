package com.javenock.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PostEventDto {
    private UUID publicId;
    private String title;
    private String createdByName;
}
