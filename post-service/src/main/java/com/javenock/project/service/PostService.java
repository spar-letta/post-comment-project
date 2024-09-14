package com.javenock.project.service;

import com.javenock.project.dto.PostDto;
import com.javenock.project.exception.MessageException;
import com.javenock.project.model.Post;
//import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface PostService {

    Post createPost(PostDto postDto, Authentication auth);

    Page<Post> fetchAllPosts(Authentication loggedInUserDetails, Pageable pageable);

    Post getPost(UUID publicId, Authentication auth) throws MessageException;

    Post updatePostContent(UUID publicId, PostDto postDto, Authentication auth);
}
