package com.taskagile.domain.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DefaultEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher actualPublisher;

    public DefaultEventPublisher(ApplicationEventPublisher actualPublisher) {
        this.actualPublisher = actualPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        actualPublisher.publishEvent(event);
    }
}
