package com.javenock.project.dto.events.tasks;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.javenock.project.dto.events.posts.PostCreationEvent;
import lombok.*;

import java.io.Serializable;
import java.time.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostCreationEvent.class, name = PostCreationEvent._TYPE)
})
public abstract class PublishableEvent<T> implements Serializable {
    private final UUID id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime eventTime;

    public PublishableEvent() {
        Instant instant = Instant.now();

        this.id = UUID.randomUUID();
        this.eventTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
