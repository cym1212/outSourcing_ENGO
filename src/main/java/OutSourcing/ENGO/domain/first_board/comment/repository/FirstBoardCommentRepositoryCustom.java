package OutSourcing.ENGO.domain.first_board.comment.repository;

import OutSourcing.ENGO.domain.first_board.comment.dto.request.FirstBoardCommentRequestDTO;
import jakarta.transaction.Transactional;

public interface FirstBoardCommentRepositoryCustom {
    @Transactional
    void updateFirstBoardComment(Long firstBoardCommentId, FirstBoardCommentRequestDTO requestDto);

    @Transactional
    void deleteFirstBoardComment(Long commentId);
}
