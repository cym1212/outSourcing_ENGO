package OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_image;

import jakarta.transaction.Transactional;

public interface GalleryImageRepositoryCustom {
    @Transactional
    void deleteGalleryImages(Long galleryBoardId);
}
