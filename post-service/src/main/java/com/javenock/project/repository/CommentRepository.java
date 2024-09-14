package com.javenock.project.repository;

import com.javenock.project.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPublicIdAndDeletedIsFalse(UUID commentId);
}
