package OutSourcing.ENGO.domain.thrid_board.third_board.repository;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdBoardRepository extends JpaRepository<ThirdBoard, Long>, ThirdBoardRepositoryCustom {
    Page<ThirdBoard> findByTitleContainingAndDeletedAtIsNull(String title, Pageable pageable);
    Page<ThirdBoard> findByContentContainingAndDeletedAtIsNull(String content, Pageable pageable);
    Page<ThirdBoard> findByMemberNameContainingAndDeletedAtIsNull(String name, Pageable pageable);
    Page<ThirdBoard> findByTitleContainingOrContentContainingOrMemberNameContainingAndDeletedAtIsNull(String title, String content, String name, Pageable pageable);
}
