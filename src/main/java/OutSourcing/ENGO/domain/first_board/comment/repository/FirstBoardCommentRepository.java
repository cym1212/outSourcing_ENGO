package OutSourcing.ENGO.domain.first_board.comment.repository;

import OutSourcing.ENGO.domain.first_board.comment.domain.FirstBoardComment;
import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstBoardCommentRepository extends JpaRepository<FirstBoardComment, Long> , FirstBoardCommentRepositoryCustom {
    Page<FirstBoardComment> findByFirstBoard(FirstBoard firstBoard, Pageable pageable);
}
