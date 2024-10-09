package OutSourcing.ENGO.domain.second_board.like.repository;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;

public interface SecondBoardLikeRepositoryCustom {
    boolean existsBySecondBoardAndMemberQueryDSL(SecondBoard secondBoard, Member member);
    long countBySecondBoardQueryDSL(SecondBoard secondBoard);
}
