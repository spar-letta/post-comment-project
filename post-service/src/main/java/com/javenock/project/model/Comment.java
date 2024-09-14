package com.javenock.project.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.project.model.dataType.CommentType;
import com.javenock.project.views.BaseView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "comments", schema = "posts")
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_body")
    @JsonView(BaseView.CommentViews.class)
    private String commentBody;

    @ManyToOne
    @JoinColumn(name = "post_comment_id", referencedColumnName = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
//    @JsonView(BaseView.CommentViews.class)
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    @JsonView(BaseView.CommentViews.class)
    private List<Comment> childrenComment = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @JsonView(BaseView.CommentViews.class)
    @Column(name = "comment_type")
    private CommentType type;
}
