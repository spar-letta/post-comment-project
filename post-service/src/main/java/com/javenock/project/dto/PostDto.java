package com.javenock.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDto {

    @NotNull
    private String title;

    @NotNull
    private String content;
}
