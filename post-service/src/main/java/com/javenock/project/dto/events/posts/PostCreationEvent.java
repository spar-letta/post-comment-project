package com.javenock.project.dto.events.posts;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.javenock.project.dto.PostEventDto;
import com.javenock.project.dto.events.tasks.PublishableEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonTypeName(PostCreationEvent._TYPE)
public class PostCreationEvent extends PublishableEvent<PostEventDto> {
    public static final String _TYPE = "postCreationEvent";
    private PostEventDto subject;

    public PostCreationEvent(PostEventDto s) {
        super();
        this.subject = s;
    }
}
