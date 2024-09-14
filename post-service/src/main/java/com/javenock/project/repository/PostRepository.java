package com.javenock.project.repository;

import com.javenock.project.model.Post;
import com.javenock.project.model.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreatedByAndDeletedIsFalse(User createdBy);

    Optional<Post> findByPublicIdAndDeletedIsFalse(UUID publicId);

    Optional<Post> findByPublicIdAndCreatedByAndDeletedIsFalse(UUID publicId, User createdBy);
}
