package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class CreateTeamCommand {
    private String name;
    private UserId userId;
}
