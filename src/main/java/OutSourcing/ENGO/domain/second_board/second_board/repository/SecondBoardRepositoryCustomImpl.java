package OutSourcing.ENGO.domain.second_board.second_board.repository;


import OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.second_board.second_board.dto.request.SecondBoardCreatRequestDTO;
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

import static OutSourcing.ENGO.domain.second_board.second_board.domain.QSecondBoard.secondBoard;


@Repository
@RequiredArgsConstructor
public class SecondBoardRepositoryCustomImpl implements SecondBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Page<SecondBoard> findNonDeletedSecondBoard(Pageable pageable) {

        JPAQuery<SecondBoard> query = queryFactory.selectFrom(secondBoard)
                .where(secondBoard.deletedAt.isNull());

        // 페이징 및 정렬 처리
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        // QueryDSL에서 FetchResults를 사용하여 페이징된 결과와 총 카운트 계산
        List<SecondBoard> secondBoardList = query.fetch(); // fetch()를 통해 결과만 가져옴
        long totalCount = queryFactory.selectFrom(secondBoard)
                .where(secondBoard.deletedAt.isNull()
                ).fetchCount(); // fetchCount()로 총 개수 계산

        // Page 객체로 변환하여 반환
        return new PageImpl<>(secondBoardList, pageable, totalCount);
    }

    @Transactional
    @Override
    public void deleteSecondBoard(Long communityId) {
        long updatedCount = queryFactory.update(secondBoard)
                .set(secondBoard.deletedAt, LocalDateTime.now())
                .where(secondBoard.id.eq(communityId).and(secondBoard.deletedAt.isNull()))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Transactional
    @Override
    public void updateSecondBoard(Long secondBoardId, SecondBoardCreatRequestDTO secondBoardCreatRequestDTO) {
        // 엔티티 조회
        SecondBoard existingSecondBoard = entityManager.find(SecondBoard.class, secondBoardId);
        if (existingSecondBoard == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }

        // QueryDSL로 업데이트
        long updatedCount = queryFactory.update(secondBoard)
                .set(secondBoard.title, secondBoardCreatRequestDTO.getTitle() != null ? secondBoardCreatRequestDTO.getTitle() : existingSecondBoard.getTitle())
                .set(secondBoard.content, secondBoardCreatRequestDTO.getContent() != null ? secondBoardCreatRequestDTO.getContent() : existingSecondBoard.getContent())
                .set(secondBoard.updatedAt, LocalDateTime.now())  // 업데이트 시각 갱신
                .where(secondBoard.id.eq(secondBoardId))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.UPDATE_FAILED);
        }
    }
}