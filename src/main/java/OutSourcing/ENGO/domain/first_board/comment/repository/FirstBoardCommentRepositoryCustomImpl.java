package OutSourcing.ENGO.domain.first_board.comment.repository;

import OutSourcing.ENGO.domain.first_board.comment.dto.request.FirstBoardCommentRequestDTO;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static OutSourcing.ENGO.domain.first_board.comment.domain.QFirstBoardComment.firstBoardComment;

@Repository
@RequiredArgsConstructor
public class FirstBoardCommentRepositoryCustomImpl implements FirstBoardCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Transactional
    @Override
    public void updateFirstBoardComment(Long firstBoardCommentId, FirstBoardCommentRequestDTO requestDto) {
        long updatedCount = queryFactory.update(firstBoardComment)
                .set(firstBoardComment.comments, requestDto.getComments())  // 댓글 내용 업데이트
                .set(firstBoardComment.updatedAt, LocalDateTime.now())  // 업데이트 시간 설정
                .where(firstBoardComment.id.eq(firstBoardCommentId)
                        .and(firstBoardComment.deletedAt.isNull()))  // 삭제되지 않은 댓글만 업데이트
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_COMMENT);  // 업데이트가 안 된 경우 예외 발생
        }
    }

    @Transactional
    @Override
    public void deleteFirstBoardComment(Long commentId) {
        long updatedCount = queryFactory.update(firstBoardComment)
                .set(firstBoardComment.deletedAt, LocalDateTime.now()) // 논리적 삭제를 위해 deletedAt 필드 설정
                .where(firstBoardComment.id.eq(commentId)
                        .and(firstBoardComment.deletedAt.isNull())) // 이미 삭제된 댓글을 다시 삭제하지 않도록 조건 추가
                .execute();

        if (updatedCount == 0) {
            throw new IllegalArgumentException("댓글을 찾을 수 없거나 이미 삭제되었습니다.");
        }
    }
}
