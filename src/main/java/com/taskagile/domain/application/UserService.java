package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.RegisterCommand;
import com.taskagile.domain.model.user.RegistrationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    void register(RegisterCommand command) throws RegistrationException;

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}