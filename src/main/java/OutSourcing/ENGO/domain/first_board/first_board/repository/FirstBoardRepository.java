package OutSourcing.ENGO.domain.first_board.first_board.repository;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstBoardRepository extends JpaRepository<FirstBoard, Long>, FirstBoardRepositoryCustom {
    Page<FirstBoard> findByTitleContainingAndDeletedAtIsNull(String title, Pageable pageable);
    Page<FirstBoard> findByContentContainingAndDeletedAtIsNull(String content, Pageable pageable);
    Page<FirstBoard> findByMemberNameContainingAndDeletedAtIsNull(String name, Pageable pageable);
    Page<FirstBoard> findByTitleContainingOrContentContainingOrMemberNameContainingAndDeletedAtIsNull(String title, String content, String name, Pageable pageable);
}
