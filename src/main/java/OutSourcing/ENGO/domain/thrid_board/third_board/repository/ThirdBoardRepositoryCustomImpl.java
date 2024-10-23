package OutSourcing.ENGO.domain.thrid_board.third_board.repository;


import OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.QThirdBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.dto.request.ThirdBoardCreatRequestDTO;
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

import static OutSourcing.ENGO.domain.thrid_board.third_board.domain.QThirdBoard.thirdBoard;


@Repository
@RequiredArgsConstructor
public class ThirdBoardRepositoryCustomImpl implements ThirdBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Page<ThirdBoard> findNonDeletedThirdBoard(Pageable pageable) {


        JPAQuery<ThirdBoard> query = queryFactory.selectFrom(thirdBoard)
                .where(thirdBoard.deletedAt.isNull())
                .orderBy(thirdBoard.createdAt.desc());  // 최신순 정렬

        List<ThirdBoard> thirdBoardList = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory.selectFrom(thirdBoard)
                .where(thirdBoard.deletedAt.isNull())
                .fetchCount();

        return new PageImpl<>(thirdBoardList, pageable, totalCount);
    }

    @Transactional
    @Override
    public void deleteThirdBoard(Long thirdBoardId) {
        long updatedCount = queryFactory.update(thirdBoard)
                .set(thirdBoard.deletedAt, LocalDateTime.now())
                .where(thirdBoard.id.eq(thirdBoardId).and(thirdBoard.deletedAt.isNull()))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Transactional
    @Override
    public void updateThirdBoard(Long thirdBoardId, ThirdBoardCreatRequestDTO thirdBoardCreatRequestDTO) {
        // 엔티티 조회
        ThirdBoard existingThirdBoard = entityManager.find(ThirdBoard.class, thirdBoardId);
        if (existingThirdBoard == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }

        // QueryDSL로 업데이트
        long updatedCount = queryFactory.update(thirdBoard)
                .set(thirdBoard.title, thirdBoardCreatRequestDTO.getTitle() != null ? thirdBoardCreatRequestDTO.getTitle() : existingThirdBoard.getTitle())
                .set(thirdBoard.content, thirdBoardCreatRequestDTO.getContent() != null ? thirdBoardCreatRequestDTO.getContent() : existingThirdBoard.getContent())
                .set(thirdBoard.updatedAt, LocalDateTime.now())  // 업데이트 시각 갱신
                .where(thirdBoard.id.eq(thirdBoardId))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.UPDATE_FAILED);
        }
    }
}