package OutSourcing.ENGO.domain.second_board.comment.repository;

import OutSourcing.ENGO.domain.second_board.comment.domain.SecondBoardComment;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondBoardCommentRepository extends JpaRepository<SecondBoardComment, Long> , SecondBoardCommentRepositoryCustom {
    Page<SecondBoardComment> findBySecondBoard(SecondBoard secondBoard, Pageable pageable);
}
