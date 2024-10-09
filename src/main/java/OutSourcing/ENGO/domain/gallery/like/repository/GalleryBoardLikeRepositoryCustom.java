package OutSourcing.ENGO.domain.gallery.like.repository;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.member.domain.Member;

public interface GalleryBoardLikeRepositoryCustom {
    boolean existsByGalleryBoardAndMemberQueryDSL(GalleryBoard galleryBoard, Member member);
    long countByGalleryBoardQueryDSL(GalleryBoard galleryBoard);
}
