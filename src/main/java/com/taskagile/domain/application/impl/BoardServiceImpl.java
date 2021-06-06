package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardManagement;
import com.taskagile.domain.model.board.BoardRepository;
import com.taskagile.domain.model.user.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final BoardManagement boardManagement;

    public BoardServiceImpl(BoardRepository boardRepository, BoardManagement boardManagement) {
        this.boardRepository = boardRepository;
        this.boardManagement = boardManagement;
    }

    @Override
    public Board createBoard(CreateBoardCommand command) {
        Board board = boardManagement.createBoard(command.getUserId(),
                command.getName(),
                command.getDescription(),
                command.getTeamId());
        // eventPublisher publish createBoardEvent
        return board;
    }

    @Override
    public List<Board> findBoardsByMembership(UserId userId) {
        return boardRepository.findBoardByMembership(userId.value());
    }
}
