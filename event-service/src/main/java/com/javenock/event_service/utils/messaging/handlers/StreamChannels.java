package com.javenock.event_service.utils.messaging.handlers;

//import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface StreamChannels {
    String TASK_EVENTS  = "post-creation-events";

//    @Input(TASK_EVENTS)
//    MessageChannel postCreated();

//    @Output(TASK_EVENTS)
    SubscribableChannel taskEvents();
}
