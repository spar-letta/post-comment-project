package com.javenock.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javenock.project.dto.events.posts.PostCreationEvent;
import com.javenock.project.dto.events.tasks.PublishableEvent;
import com.javenock.project.utils.StreamChannels;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;
import static io.vavr.API.run;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final MessageChannel taskEventsChannel;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public EventServiceImpl(StreamChannels streamChannels) {
        this.taskEventsChannel = streamChannels.taskEventsChannel();
    }

    @Override
    public void publishEvent(PublishableEvent event) {
        Match(event).of(
                Case($(instanceOf(PostCreationEvent.class)), evt -> run(() -> handleTaskEvent(evt))),
                Case($(), evt -> run(() -> log.warn("Unhandled event {}", evt.getClass().getName())))
                );
    }

    private void handleTaskEvent(PostCreationEvent event) {
        try {
            log.info("EventService handleTaskEvent {}", objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Try.of(() -> objectMapper.writeValueAsString(event))
                .map(str -> MessageBuilder.withPayload(str).build())
                .andThen(taskEventsChannel::send);
    }
}
