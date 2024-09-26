package com.javenock.event_service.domain.dto.events.posts;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.javenock.event_service.domain.dto.PostEventDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonTypeName(PostCreationEvent._TYPE)
public class PostCreationEvent extends PostCommentEvent {
    public static final String _TYPE = "postCreationEvent";

    private PostEventDto subject;
}
