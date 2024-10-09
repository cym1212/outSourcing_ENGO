package OutSourcing.ENGO.domain.gallery.gallery_board.repository;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.request.GalleryBoardCreatRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GalleryBoardRepositoryCustom {
    @Transactional
    Page<GalleryBoard> findNonDeletedGalleryBoard(Pageable pageable);

    @Transactional
    void deleteGalleryBoard(Long galleryBoardId);

    @Transactional
    void updateGalleryBoard(Long galleryBoardId, GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO);
}
