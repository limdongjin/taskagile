package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.UserService;
import com.taskagile.domain.application.commands.RegisterCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.common.mail.MessageVariable;
import com.taskagile.domain.model.user.*;
import com.taskagile.domain.model.user.events.UserRegisteredEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private RegistrationManagement registrationManagement;
    private DomainEventPublisher domainEventPublisher;
    private UserRepository userRepository;
    private MailManager mailManager;

    public UserServiceImpl(RegistrationManagement registrationManagement, DomainEventPublisher domainEventPublisher, UserRepository userRepository, MailManager mailManager) {
        this.registrationManagement = registrationManagement;
        this.domainEventPublisher = domainEventPublisher;
        this.userRepository = userRepository;
        this.mailManager = mailManager;
    }

    @Override
    public void register(@NotNull RegisterCommand command) throws RegistrationException {
        User newUser = registrationManagement.register(command.getUsername(),
                command.getEmailAddress(),
                command.getPassword());
        sendWelcomeMessage(newUser);
        domainEventPublisher.publish(new UserRegisteredEvent(newUser));
    }

    private void sendWelcomeMessage(User user) {
        mailManager.send(user.getEmailAddress(),
                "Welcome to TaskAgile",
                "welcome.ftl",
                MessageVariable.from("user", user));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(s)){
            throw new UsernameNotFoundException("No user found.");
        }

        User user;
        if(s.contains("@")){
            user = userRepository.findByEmailAddress(s);
        }else{
            user = userRepository.findByUsername(s);
        }

        if (user == null){
            throw new UsernameNotFoundException("No user found " + s);
        }
        return new SimpleUser(user);
    }
}
