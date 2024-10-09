package OutSourcing.ENGO.domain.gallery.comment.repository;

import OutSourcing.ENGO.domain.gallery.comment.domain.GalleryBoardComment;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryBoardCommentRepository extends JpaRepository<GalleryBoardComment, Long> , GalleryBoardCommentRepositoryCustom {
    Page<GalleryBoardComment> findByGalleryBoard(GalleryBoard galleryBoard, Pageable pageable);
}
