package OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_image;

import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static OutSourcing.ENGO.domain.gallery.gallery_board.domain.QGalleryImage.galleryImage;

@Repository
@RequiredArgsConstructor
public class GalleryImageRepositoryCustomImpl implements GalleryImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Transactional
    @Override
    public void deleteGalleryImages(Long galleryBoardId) {
        long deletedCount = queryFactory.update(galleryImage)
                .set(galleryImage.deletedAt, LocalDateTime.now())  // 논리적 삭제 처리
                .where(galleryImage.galleryBoard.id.eq(galleryBoardId))
                .execute();

        if (deletedCount == 0) {
            throw new BusinessException(ErrorCode.IMAGE_NOT_FOUND);
        }
    }
}
