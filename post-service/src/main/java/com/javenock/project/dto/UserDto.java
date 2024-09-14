package com.javenock.project.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.project.views.BaseView;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID publicId;

    private String firstName;

    private String contactEmail;

    private String userName;
}
