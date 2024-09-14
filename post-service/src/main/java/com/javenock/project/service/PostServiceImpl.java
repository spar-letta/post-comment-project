package com.javenock.project.service;

import com.javenock.project.dto.PostDto;
import com.javenock.project.dto.PostEventDto;
import com.javenock.project.dto.events.posts.PostCreationEvent;
import com.javenock.project.exception.MessageException;
import com.javenock.project.model.Post;
import com.javenock.project.model.vo.User;
import com.javenock.project.repository.PostRepository;
import com.javenock.project.repository.UserRepository;
import com.javenock.project.utils.UserManagementServiceClientImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserManagementServiceClientImpl userManagementServiceClient;
    private final UserRepository userRepository;
    private final EventService eventService;

    @Override
    public Post createPost(PostDto postDto, Authentication auth) {
        User user = userRepository.findByUserName(auth.getName());
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDateCreated(LocalDateTime.now());
        post.setCreatedBy(user);
        Post savedPost = postRepository.save(post);

        PostEventDto postEventDto = convertToPostEventDto(savedPost);
        eventService.publishEvent(new PostCreationEvent(postEventDto));
        return savedPost;
    }

    @Override
    public Page<Post> fetchAllPosts(Authentication loggedInUserDetails, Pageable pageable) {
        User user = userRepository.findByUserName(loggedInUserDetails.getName());
        if (user != null) {
            List<Post> foundPosts = postRepository.findByCreatedByAndDeletedIsFalse(user);
            return new PageImpl<>(foundPosts, pageable, foundPosts.size());
        }
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPost(UUID publicId, Authentication auth) throws MessageException {
        return postRepository.findByPublicIdAndDeletedIsFalse(publicId).orElseThrow(
                () -> new MessageException("Post not found")
        );
    }

    @Override
    public Post updatePostContent(UUID publicId, PostDto postDto, Authentication auth) {
        User user = userRepository.findByUserName(auth.getName());
        if (user != null) {
            Post post = postRepository.findByPublicIdAndCreatedByAndDeletedIsFalse(publicId, user).orElse(null);
            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setModifiedBy(auth.getName());
            return postRepository.save(post);
        }
        return null;
    }

    private PostEventDto convertToPostEventDto(Post savedPost) {
        PostEventDto postEventDto = new PostEventDto();
        postEventDto.setPublicId(savedPost.getPublicId());
        postEventDto.setTitle(savedPost.getTitle());
        postEventDto.setCreatedByName(savedPost.getCreatedBy().getFirstName());
        return postEventDto;
    }
}
