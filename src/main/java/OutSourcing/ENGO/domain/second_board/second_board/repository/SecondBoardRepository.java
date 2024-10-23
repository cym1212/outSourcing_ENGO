package OutSourcing.ENGO.domain.second_board.second_board.repository;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondBoardRepository extends JpaRepository<SecondBoard, Long>, SecondBoardRepositoryCustom {
    Page<SecondBoard> findByTitleContainingAndDeletedAtIsNull(String title, Pageable pageable);
    Page<SecondBoard> findByContentContainingAndDeletedAtIsNull(String content, Pageable pageable);
    Page<SecondBoard> findByMemberNameContainingAndDeletedAtIsNull(String name, Pageable pageable);
    Page<SecondBoard> findByTitleContainingOrContentContainingOrMemberNameContainingAndDeletedAtIsNull(String title, String content, String name, Pageable pageable);
}
