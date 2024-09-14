package com.javenock.project.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.project.docs.Examples;
import com.javenock.project.dto.CommentDto;
import com.javenock.project.exception.MessageException;
import com.javenock.project.model.Comment;
import com.javenock.project.model.dataType.CommentType;
import com.javenock.project.service.CommentService;
import com.javenock.project.views.BaseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "comment")
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_COMMENT')")
    @JsonView(BaseView.CommentViews.class)
    public Comment createComment(@RequestBody @Valid CommentDto commentDto, Authentication auth) throws MessageException {
        return commentService.create(commentDto, auth);
    }

    @GetMapping
    @JsonView(BaseView.CommentViews.class)
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(example = Examples.GET_PARENT_COMMENT_OK_RESPONSE))),
    })
    public Page<Comment> getComments(@RequestParam(name = "type") CommentType type,
                                     @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Authentication auth) {
        return commentService.fetchAllComments(pageable, type, auth);
    }

    @GetMapping("/{publicId}")
    @PreAuthorize("hasAuthority('READ_COMMENT')")
    @JsonView(BaseView.CommentViews.class)
    public Comment getComment(@PathVariable String publicId, Authentication auth) {
        return commentService.getComment(publicId, auth);
    }

}
