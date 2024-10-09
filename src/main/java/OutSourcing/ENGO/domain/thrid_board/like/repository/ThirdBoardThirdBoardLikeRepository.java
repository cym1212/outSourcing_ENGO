package OutSourcing.ENGO.domain.thrid_board.like.repository;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import OutSourcing.ENGO.domain.thrid_board.like.domain.ThirdBoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThirdBoardThirdBoardLikeRepository extends JpaRepository<ThirdBoardLike, Long>, ThirdBoardLikeRepositoryCustom {
    Optional<ThirdBoardLike> findByThirdBoardAndMember(ThirdBoard thirdBoard, Member member);
}
