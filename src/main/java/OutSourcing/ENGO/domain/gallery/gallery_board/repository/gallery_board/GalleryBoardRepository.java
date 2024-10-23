package OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_board;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryBoardRepository extends JpaRepository<GalleryBoard, Long>, GalleryBoardRepositoryCustom {

    Page<GalleryBoard> findByTitleContainingAndDeletedAtIsNull(String title, Pageable pageable);
    Page<GalleryBoard> findByContentContainingAndDeletedAtIsNull(String content, Pageable pageable);
    Page<GalleryBoard> findByMemberNameContainingAndDeletedAtIsNull(String name, Pageable pageable);
    Page<GalleryBoard> findByTitleContainingOrContentContainingOrMemberNameContainingAndDeletedAtIsNull(String title, String content, String name, Pageable pageable);
}
