package com.javenock.project.service;

import com.javenock.project.dto.CommentDto;
import com.javenock.project.exception.MessageException;
import com.javenock.project.model.Comment;
import com.javenock.project.model.dataType.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface CommentService {
    Comment create(CommentDto commentDto, Authentication auth) throws MessageException;

    Comment getComment(String publicId, Authentication auth);

    Page<Comment> fetchAllComments(Pageable pageable, CommentType type, Authentication auth);
}
