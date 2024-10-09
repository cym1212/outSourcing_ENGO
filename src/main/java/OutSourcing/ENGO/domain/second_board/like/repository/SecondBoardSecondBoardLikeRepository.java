package OutSourcing.ENGO.domain.second_board.like.repository;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.second_board.like.domain.SecondBoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecondBoardSecondBoardLikeRepository extends JpaRepository<SecondBoardLike, Long>, SecondBoardLikeRepositoryCustom {
    Optional<SecondBoardLike> findBySecondBoardAndMember(SecondBoard secondBoard, Member member);
}
