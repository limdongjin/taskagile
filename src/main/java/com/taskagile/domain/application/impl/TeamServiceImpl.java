package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.team.TeamRepository;
import com.taskagile.domain.model.user.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findTeamByUserId(UserId userId) {
        return teamRepository.findTeamsByUserId(userId.value());
    }

    public Team createTeam(CreateTeamCommand command){
        Team team = Team.create(command.getName(), command.getUserId());
        teamRepository.save(team);

        // domainEventPublisher.publish(new TeamCreateEvent(this, team))

        return team;
    }
}
