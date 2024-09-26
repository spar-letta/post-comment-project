package com.javenock.event_service.utils;

import com.javenock.event_service.config.ReportProcessingConfig;
import com.javenock.event_service.domain.dto.events.posts.PostCommentEvent;
import com.javenock.event_service.domain.dto.events.posts.PostCreationEvent;
import com.javenock.event_service.service.PostReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@RequiredArgsConstructor
@Component
public class handPostCreatedEvent {

    private final PostReportService reportService;

    @RabbitListener(queues = ReportProcessingConfig.REPORT_QUEUE)
    public void handlePostReportEvent(PostCommentEvent event) {
        Match(event).of(
                Case($(instanceOf(PostCreationEvent.class)), evt -> run(() -> handlePostCreationEvent(evt))),
                Case($(), o -> run(() -> log.warn("Don't know what to do"))));
    }

    private void handlePostCreationEvent(PostCreationEvent evt) {
        try {
            log.info("post data {}." + ",", evt);
            reportService.createPostReport(evt.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





