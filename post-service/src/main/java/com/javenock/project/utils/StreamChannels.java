package com.javenock.project.utils;

import org.springframework.cloud.stream.annotation.*;
import org.springframework.messaging.MessageChannel;

public interface StreamChannels {
    String TASK_EVENTS                   = "post-creation-events";


    @Output(TASK_EVENTS)
    MessageChannel taskEventsChannel();
}
