package com.taskagile.domain.application;

import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.UserId;

import java.util.List;

public interface TeamService {
    List<Team> findTeamByUserId(UserId userId);
}
