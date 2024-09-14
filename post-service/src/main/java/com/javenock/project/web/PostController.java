package com.javenock.project.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.project.dto.PostDto;
import com.javenock.project.exception.MessageException;
import com.javenock.project.model.Post;
import com.javenock.project.service.PostService;
import com.javenock.project.views.BaseView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/post",  produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_POST')")
    @JsonView({BaseView.PostViews.class})
    public Post createPost(@RequestBody PostDto postDto, Authentication auth) {
        return postService.createPost(postDto, auth);
    }

    @PreAuthorize("hasAuthority('READ_POSTS')")
    @GetMapping
    @JsonView({BaseView.PostViews.class})
    @PageableAsQueryParam
    public Page<Post> getPosts(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                               Authentication loggedInUserDetails) {
        return postService.fetchAllPosts(loggedInUserDetails, pageable);
    }

    @GetMapping("/{publicId}")
    @PreAuthorize("hasAuthority('READ_POSTS')")
    @JsonView(BaseView.PostViews.class)
    public Post getPost(@PathVariable("publicId") UUID publicId, Authentication auth) throws MessageException {
        return postService.getPost(publicId, auth);
    }

    @PatchMapping("/{publicId}")
    @PreAuthorize("hasAuthority('CREATE_POST')")
    @JsonView(BaseView.PostViews.class)
    public Post updatePostContent(@PathVariable("publicId") UUID publicId, @RequestBody @Valid PostDto postDto, Authentication auth) {
        return postService.updatePostContent(publicId, postDto, auth);
    }
}
