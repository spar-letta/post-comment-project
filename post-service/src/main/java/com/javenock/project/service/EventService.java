package com.javenock.project.service;

import com.javenock.project.dto.events.tasks.PublishableEvent;

public interface EventService {
    void publishEvent(PublishableEvent event);
}
