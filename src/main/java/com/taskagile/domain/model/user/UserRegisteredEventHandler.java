package com.taskagile.domain.model.user;

import com.taskagile.domain.model.user.events.UserRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventHandler {
    @EventListener(UserRegisteredEvent.class)
    public void handleEvent(UserRegisteredEvent event){
        System.out.println(("Handling registration event " + event.getUser().getEmailAddress()));
    }
}
