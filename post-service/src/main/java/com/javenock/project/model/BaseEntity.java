package com.javenock.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.javenock.project.model.vo.User;
import com.javenock.project.views.BaseView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "public_id")
    @JsonView({BaseView.PostViews.class, BaseView.CommentViews.class})
    private UUID publicId;

    @CreatedDate
    @Column(name = "date_created", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView({BaseView.PostViews.class, BaseView.CommentViews.class})
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateModified;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @JsonView({BaseView.PostViews.class, BaseView.CommentViews.class})
    private User createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonView({BaseView.PostViews.class})
    private String modifiedBy;

    @Column(name = "deleted")
    private boolean deleted;

    public void touch() {
        this.dateModified = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.publicId = UUID.randomUUID();
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.deleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModified = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    }
}
