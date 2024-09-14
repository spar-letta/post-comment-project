package com.javenock.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentDto {

    @NotNull
    private UUID postId;

    @NotNull
    private String commentBody;

    private UUID commentId;
}
