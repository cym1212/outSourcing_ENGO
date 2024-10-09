package OutSourcing.ENGO.domain.second_board.second_board.repository;

import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondBoardRepository extends JpaRepository<SecondBoard, Long>, SecondBoardRepositoryCustom {
}
