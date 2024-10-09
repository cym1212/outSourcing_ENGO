package OutSourcing.ENGO.domain.gallery.gallery_board.repository;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryBoardRepository extends JpaRepository<GalleryBoard, Long>, GalleryBoardRepositoryCustom {
}
