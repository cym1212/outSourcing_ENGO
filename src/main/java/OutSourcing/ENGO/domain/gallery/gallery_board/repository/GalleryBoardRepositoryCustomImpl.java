package OutSourcing.ENGO.domain.gallery.gallery_board.repository;


import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.request.GalleryBoardCreatRequestDTO;
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

import static OutSourcing.ENGO.domain.gallery.gallery_board.domain.QGalleryBoard.galleryBoard;


@Repository
@RequiredArgsConstructor
public class GalleryBoardRepositoryCustomImpl implements GalleryBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Page<GalleryBoard> findNonDeletedGalleryBoard(Pageable pageable) {

        JPAQuery<GalleryBoard> query = queryFactory.selectFrom(galleryBoard)
                .where(galleryBoard.deletedAt.isNull());

        // 페이징 및 정렬 처리
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        // QueryDSL에서 FetchResults를 사용하여 페이징된 결과와 총 카운트 계산
        List<GalleryBoard> galleryBoardList = query.fetch(); // fetch()를 통해 결과만 가져옴
        long totalCount = queryFactory.selectFrom(galleryBoard)
                .where(galleryBoard.deletedAt.isNull()
                ).fetchCount(); // fetchCount()로 총 개수 계산

        // Page 객체로 변환하여 반환
        return new PageImpl<>(galleryBoardList, pageable, totalCount);
    }

    @Transactional
    @Override
    public void deleteGalleryBoard(Long galleryBoardId) {
        long updatedCount = queryFactory.update(galleryBoard)
                .set(galleryBoard.deletedAt, LocalDateTime.now())
                .where(galleryBoard.id.eq(galleryBoardId).and(galleryBoard.deletedAt.isNull()))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }
    }

    @Transactional
    @Override
    public void updateGalleryBoard(Long galleryBoardId, GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO) {
        // 엔티티 조회
        GalleryBoard existingGalleryBoard = entityManager.find(GalleryBoard.class, galleryBoardId);
        if (existingGalleryBoard == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }

        // QueryDSL로 업데이트
        long updatedCount = queryFactory.update(galleryBoard)
                .set(galleryBoard.title, galleryBoardCreatRequestDTO.getTitle() != null ? galleryBoardCreatRequestDTO.getTitle() : existingGalleryBoard.getTitle())
                .set(galleryBoard.content, galleryBoardCreatRequestDTO.getContent() != null ? galleryBoardCreatRequestDTO.getContent() : existingGalleryBoard.getContent())
                .set(galleryBoard.updatedAt, LocalDateTime.now())  // 업데이트 시각 갱신
                .where(galleryBoard.id.eq(galleryBoardId))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.UPDATE_FAILED);
        }
    }
}