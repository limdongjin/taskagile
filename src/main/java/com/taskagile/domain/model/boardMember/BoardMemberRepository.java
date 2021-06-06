package com.taskagile.domain.model.boardMember;

import com.taskagile.domain.model.board.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {

}
