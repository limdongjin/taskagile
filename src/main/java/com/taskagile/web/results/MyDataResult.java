package com.taskagile.web.results;

import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MyDataResult {
    public static ResponseEntity<ApiResult> build(SimpleUser currentUser,
                                                  List<Team> teams,
                                                  List<Board> boards){
        List<TeamResult> teamResults = teams.stream()
                .map(TeamResult::new)
                .collect(Collectors.toList());
        List<BoardResult> boardResults = boards.stream()
                .map(BoardResult::new)
                .collect(Collectors.toList());
        ApiResult apiResult = ApiResult.blank()
                .add("name", currentUser.getUsername())
                .add("user", currentUser)
                .add("teams", teamResults)
                .add("boards", boardResults);

        return ResponseEntity.ok(apiResult);
    }

    @Getter
    public static class TeamResult {
        private final long id;
        private final String name;

        public TeamResult(Team team) {
            this.id = team.getId().value();
            this.name = team.getName();
        }
    }

    @Getter
    public static class BoardResult {
        private final long id;
        private final String name;
        private final String description;
        private final long teamId;

        public BoardResult(Board board) {
            this.id = board.obtainBoardId().value();
            this.name = board.getName();
            this.description = board.getDescription();
            this.teamId = board.getTeamId();
        }
    }
}
