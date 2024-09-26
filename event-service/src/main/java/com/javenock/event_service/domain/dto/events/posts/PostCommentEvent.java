package com.javenock.event_service.domain.dto.events.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.javenock.event_service.domain.dto.events.PostReportEventDefault;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = PostReportEventDefault.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostCreationEvent.class, name = PostCreationEvent._TYPE),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class PostCommentEvent {
    private UUID id;
    private String eventTime;
}
