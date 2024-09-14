package com.javenock.project.service;

import com.javenock.project.dto.CommentDto;
import com.javenock.project.exception.MessageException;
import com.javenock.project.model.Comment;
import com.javenock.project.model.Post;
import com.javenock.project.model.dataType.CommentType;
import com.javenock.project.model.vo.User;
import com.javenock.project.repository.CommentRepository;
import com.javenock.project.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment create(CommentDto commentDto, Authentication auth) throws MessageException {
        User user = userRepository.findByUserName(auth.getName());
        Post post = postService.getPost(commentDto.getPostId(), auth);
        Comment comment;
        if (commentDto.getCommentId() != null) {
            Optional<Comment> optionalComment = commentRepository.findByPublicIdAndDeletedIsFalse(commentDto.getCommentId());
            if (optionalComment.isPresent()) {
                Comment parentComment = optionalComment.get();
                comment = new Comment();
                comment.setCommentBody(commentDto.getCommentBody());
                comment.setCreatedBy(user);
                comment.setPost(post);
                comment.setType(CommentType.CHILD);
                comment.setParentComment(parentComment);
                commentRepository.save(comment);
                return comment;
            }
        }
        comment = new Comment();
        comment.setCommentBody(commentDto.getCommentBody());
        comment.setCreatedBy(user);
        comment.setPost(post);
        comment.setType(CommentType.PARENT);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Comment getComment(String publicId, Authentication auth) {
        return commentRepository.findByPublicIdAndDeletedIsFalse(UUID.fromString(publicId)).orElseThrow(
                () -> new RuntimeException("Comment not found")
        );
    }

    @Override
    public Page<Comment> fetchAllComments(Pageable pageable, CommentType type, Authentication auth) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> root = cq.from(Comment.class);

        cq.distinct(true);

        final List<Predicate> andPredicates = new ArrayList<>();

        Predicate deletedPredicate = cb.equal(root.get("deleted"), Boolean.FALSE);

        andPredicates.add(deletedPredicate);

        if (type != null) {
            Predicate newPredicate = cb.equal(root.get("type"), type);
            andPredicates.add(newPredicate);
        }

        cq.where(andPredicates.toArray(new Predicate[andPredicates.size()])).orderBy(cb.desc(root.get("id")));

        TypedQuery<Comment> query = entityManager.createQuery(cq).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<Comment> queryResultList = query.getResultList();

        return new PageImpl<>(queryResultList, pageable, queryResultList.size());
    }
}
