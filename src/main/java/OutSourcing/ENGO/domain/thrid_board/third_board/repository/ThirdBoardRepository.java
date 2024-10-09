package OutSourcing.ENGO.domain.thrid_board.third_board.repository;

import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdBoardRepository extends JpaRepository<ThirdBoard, Long>, ThirdBoardRepositoryCustom {
}
