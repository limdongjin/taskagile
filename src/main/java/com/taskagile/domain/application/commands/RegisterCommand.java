package com.taskagile.domain.application.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class RegisterCommand {
    private String username;
    private String emailAddress;
    private String password;

    public RegisterCommand(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}