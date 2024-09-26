package com.javenock.event_service.domain.dto.events;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.javenock.event_service.domain.dto.events.posts.PostCommentEvent;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonTypeName(PostReportEventDefault._TYPE)
public class PostReportEventDefault extends PostCommentEvent {
    public static final String _TYPE = "default";

    private final Map<String, Object> properties = new LinkedHashMap<>();

    public PostReportEventDefault() {
        super();
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        properties.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> properties() {
        return properties;
    }
}
