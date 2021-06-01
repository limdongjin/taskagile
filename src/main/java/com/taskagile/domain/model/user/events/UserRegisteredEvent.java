package com.taskagile.domain.model.user.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.user.User;

public class UserRegisteredEvent extends DomainEvent {
    private User user;

    public UserRegisteredEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
