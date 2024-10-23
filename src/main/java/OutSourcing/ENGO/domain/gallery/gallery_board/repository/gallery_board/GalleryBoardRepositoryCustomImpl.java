package OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_board;


import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.QGalleryBoard;
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
                .where(galleryBoard.deletedAt.isNull())
                .orderBy(galleryBoard.createdAt.desc());  // 최신순 정렬

        List<GalleryBoard> galleryBoardList = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory.selectFrom(galleryBoard)
                .where(galleryBoard.deletedAt.isNull())
                .fetchCount();

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
        GalleryBoard existingGalleryBoard = entityManager.find(GalleryBoard.class, galleryBoardId);
        if (existingGalleryBoard == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST);
        }

        // QueryDSL로 업데이트
        long updatedCount = queryFactory.update(galleryBoard)
                .set(galleryBoard.title, galleryBoardCreatRequestDTO.getTitle() != null ? galleryBoardCreatRequestDTO.getTitle() : existingGalleryBoard.getTitle())
                .set(galleryBoard.content, galleryBoardCreatRequestDTO.getContent() != null ? galleryBoardCreatRequestDTO.getContent() : existingGalleryBoard.getContent())
                .set(galleryBoard.updatedAt, LocalDateTime.now())
                .where(galleryBoard.id.eq(galleryBoardId))
                .execute();

        if (updatedCount == 0) {
            throw new BusinessException(ErrorCode.UPDATE_FAILED);
        }
    }
}