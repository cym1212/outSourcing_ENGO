package OutSourcing.ENGO.domain.first_board.first_board.repository;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstBoardRepository extends JpaRepository<FirstBoard, Long>, FirstBoardRepositoryCustom {
}
