package com.javenock.event_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportProcessingConfig {

    public static final String REPORT_DIRECT_EXCHANGE = "report-direct-exchange";
    public static final String REPORT_QUEUE = "report-queue";

    // Routing keys for different order processing stages
    public static final String ROUTING_KEY_REPORT = "report";

    @Bean
    public DirectExchange reportDirectExchange() {
        return new DirectExchange(REPORT_DIRECT_EXCHANGE);
    }

    @Bean
    public Queue reportQueue() {
        return new Queue(REPORT_QUEUE);
    }

    @Bean
    public Binding reportBinding() {
        return BindingBuilder.bind(reportQueue()).to(reportDirectExchange()).with(ROUTING_KEY_REPORT);
    }
}

