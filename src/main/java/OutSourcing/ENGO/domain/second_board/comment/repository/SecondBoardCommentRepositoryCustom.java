package OutSourcing.ENGO.domain.second_board.comment.repository;

import OutSourcing.ENGO.domain.second_board.comment.dto.request.SecondBoardCommentRequestDTO;
import jakarta.transaction.Transactional;

public interface SecondBoardCommentRepositoryCustom {
    @Transactional
    void updateSecondBoardComment(Long secondBoardCommentId, SecondBoardCommentRequestDTO requestDto);

    @Transactional
    void deleteSecondBoardComment(Long secondBoardCommentId);
}
