package OutSourcing.ENGO.domain.gallery.comment.repository;

import OutSourcing.ENGO.domain.gallery.comment.dto.request.GalleryBoardCommentRequestDTO;
import jakarta.transaction.Transactional;

public interface GalleryBoardCommentRepositoryCustom {
    @Transactional
    void updateGalleryBoardComment(Long galleryBoardCommentId, GalleryBoardCommentRequestDTO requestDto);

    @Transactional
    void deleteGalleryBoardComment(Long galleryBoardCommentId);
}
