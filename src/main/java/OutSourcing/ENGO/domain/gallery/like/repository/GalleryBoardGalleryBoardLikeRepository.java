package OutSourcing.ENGO.domain.gallery.like.repository;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.like.domain.GalleryBoardLike;
import OutSourcing.ENGO.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GalleryBoardGalleryBoardLikeRepository extends JpaRepository<GalleryBoardLike, Long>, GalleryBoardLikeRepositoryCustom {
    Optional<GalleryBoardLike> findByGalleryBoardAndMember(GalleryBoard galleryBoard, Member member);
}
