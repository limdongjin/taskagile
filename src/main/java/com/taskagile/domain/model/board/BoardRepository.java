package com.taskagile.domain.model.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b left join BoardMember b_m on b.id = b_m.id.boardId where b_m.id.userId = :userId")
    List<Board> findBoardByMembership(Long userId);
}
