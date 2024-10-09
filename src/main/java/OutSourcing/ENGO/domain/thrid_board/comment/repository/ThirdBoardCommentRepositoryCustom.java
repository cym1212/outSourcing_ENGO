package OutSourcing.ENGO.domain.thrid_board.comment.repository;

import OutSourcing.ENGO.domain.thrid_board.comment.dto.request.ThirdBoardCommentRequestDTO;
import jakarta.transaction.Transactional;

public interface ThirdBoardCommentRepositoryCustom {
    @Transactional
    void updateThirdBoardComment(Long secondBoardCommentId, ThirdBoardCommentRequestDTO requestDto);

    @Transactional
    void deleteThirdBoardComment(Long secondBoardCommentId);
}
