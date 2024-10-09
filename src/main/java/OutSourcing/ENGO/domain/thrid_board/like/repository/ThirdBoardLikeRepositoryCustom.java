package OutSourcing.ENGO.domain.thrid_board.like.repository;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;

public interface ThirdBoardLikeRepositoryCustom {
    boolean existsByThirdBoardAndMemberQueryDSL(ThirdBoard thirdBoard, Member member);
    long countByThirdBoardQueryDSL(ThirdBoard thirdBoard);
}
