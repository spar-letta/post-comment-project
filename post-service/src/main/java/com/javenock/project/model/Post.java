package com.javenock.project.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.project.views.BaseView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "tb_posts", schema = "posts")
public class Post extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @JsonView({BaseView.PostViews.class})
    private String title;

    @Column(name = "content")
    @JsonView({BaseView.PostViews.class})
    private String content;

    @OneToMany(mappedBy = "post")
    @JsonView(BaseView.CommentViews.class)
    private List<Comment> comments = new ArrayList<>();
}
