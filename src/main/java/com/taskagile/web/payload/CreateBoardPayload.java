package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
public class CreateBoardPayload {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private long teamId;

    public CreateBoardCommand toCommand(UserId userId){
        return new CreateBoardCommand(userId, name, description, new TeamId(teamId));
    }
}
