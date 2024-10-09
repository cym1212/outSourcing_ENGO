package OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_image;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long>, GalleryImageRepositoryCustom {

    List<GalleryImage> findByGalleryBoardAndDeletedAtIsNull(GalleryBoard galleryBoard);
}
