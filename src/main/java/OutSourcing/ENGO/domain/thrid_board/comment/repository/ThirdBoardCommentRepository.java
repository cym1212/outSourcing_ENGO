package OutSourcing.ENGO.domain.thrid_board.comment.repository;

import OutSourcing.ENGO.domain.thrid_board.comment.domain.ThirdBoardComment;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdBoardCommentRepository extends JpaRepository<ThirdBoardComment, Long> , ThirdBoardCommentRepositoryCustom {
    Page<ThirdBoardComment> findByThirdBoard(ThirdBoard thirdBoard, Pageable pageable);
}
