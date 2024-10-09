package OutSourcing.ENGO.domain.first_board.like.repository;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.like.domain.Like;
import OutSourcing.ENGO.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {
    Optional<Like> findByFirstBoardAndMember(FirstBoard firstBoard, Member member);
}
