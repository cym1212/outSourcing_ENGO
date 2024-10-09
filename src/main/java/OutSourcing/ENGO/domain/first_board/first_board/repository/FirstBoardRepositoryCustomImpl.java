package OutSourcing.ENGO.domain.first_board.first_board.repository;


import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard;
import OutSourcing.ENGO.domain.first_board.first_board.dto.request.FirstBoardCreatRequestDTO;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard.firstBoard;


@Repository
@RequiredArgsConstructor
public class FirstBoardRepositoryCustomImpl implements FirstBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Page<FirstBoard> findNonDeletedFirstBoard(Pageable pageable) {
        QFirstBoard firstBoard = QFirstBoard.firstBoard;

        JPAQuery<FirstBoard> query = queryFactory.selectFrom(firstBoard)
                .where(firstBoard.deletedAt.isNull());

        // 페이징 및 정렬 처리
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        // QueryDSL에서 FetchResults를 사용하여 페이징된 결과와 총 카운트 계산
        List<FirstBoard> FirstBoardList = query.fetch(); // fetch()를 통해 결과만 가져옴
        long totalCount = queryFactory.selectFrom(firstBoard)
                .where(firstBoard.deletedAt.isNull()
                ).fetchCount(); // fetchCount()로 총 개수 계산

        // Page 객체로 변환하여 반환
        return new PageImpl<>(FirstBoardList, pageable, totalCount);
    }

    @Transactional
    @Override
    public void deleteFirstBoard(Long firstBoardId) {
        long updatedCount = queryFactory.update(firstBoard)
                .set(firstBoard.deletedAt, LocalDateTime.now())
                .where(firstBoard.id.eq(firstBoardId).and(firstBoard.deletedAt.isNull()))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Transactional
    @Override
    public void updateFirstBoard(Long firstBoardId, FirstBoardCreatRequestDTO firstBoardCreatRequestDTO) {
        // 엔티티 조회
        FirstBoard existingFirstBoard = entityManager.find(FirstBoard.class, firstBoardId);
        if (existingFirstBoard == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }

        // QueryDSL로 업데이트
        long updatedCount = queryFactory.update(firstBoard)
                .set(firstBoard.title, firstBoardCreatRequestDTO.getTitle() != null ? firstBoardCreatRequestDTO.getTitle() : existingFirstBoard.getTitle())
                .set(firstBoard.content, firstBoardCreatRequestDTO.getContent() != null ? firstBoardCreatRequestDTO.getContent() : existingFirstBoard.getContent())
                .set(firstBoard.updatedAt, LocalDateTime.now())  // 업데이트 시각 갱신
                .where(firstBoard.id.eq(firstBoardId))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.UPDATE_FAILED);
        }
    }
}