package OutSourcing.ENGO.domain.first_board.like.repository;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.member.domain.Member;

public interface LikeRepositoryCustom {
    boolean existsByFirstBoardAndMemberQueryDSL(FirstBoard firstBoard, Member member);
    long countByFirstBoardQueryDSL(FirstBoard firstBoard);
}
