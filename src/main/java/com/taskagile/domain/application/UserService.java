package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.RegisterCommand;
import com.taskagile.domain.model.user.RegistrationException;

public interface UserService {
    void register(RegisterCommand command) throws RegistrationException;
}