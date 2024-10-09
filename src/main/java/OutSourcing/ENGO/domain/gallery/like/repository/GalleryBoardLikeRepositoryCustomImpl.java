package OutSourcing.ENGO.domain.gallery.like.repository;

import OutSourcing.ENGO.domain.first_board.like.domain.QLike;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static OutSourcing.ENGO.domain.gallery.like.domain.QGalleryBoardLike.galleryBoardLike;

@Repository
@RequiredArgsConstructor
public class GalleryBoardLikeRepositoryCustomImpl implements GalleryBoardLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByGalleryBoardAndMemberQueryDSL(GalleryBoard galleryBoard, Member member) {

        return queryFactory
                .selectOne()
                .from(galleryBoardLike)
                .where(galleryBoardLike.galleryBoard.eq(galleryBoard)
                        .and(galleryBoardLike.member.eq(member)))
                .fetchFirst() != null;
    }

    @Override
    public long countByGalleryBoardQueryDSL(GalleryBoard galleryBoard) {


        Long count = queryFactory
                .select(galleryBoardLike.count())
                .from(galleryBoardLike)
                .where(galleryBoardLike.galleryBoard.eq(galleryBoard))
                .fetchOne();

        return count != null ? count : 0L;
    }
}