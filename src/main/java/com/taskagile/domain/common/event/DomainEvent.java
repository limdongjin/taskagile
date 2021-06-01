package com.taskagile.domain.common.event;

import java.util.Date;

public abstract class DomainEvent {
    private String userId;
    private String IpAddress;
    private Date occurredAt;
}
