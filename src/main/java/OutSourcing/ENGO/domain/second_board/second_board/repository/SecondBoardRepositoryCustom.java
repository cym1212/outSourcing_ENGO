package OutSourcing.ENGO.domain.second_board.second_board.repository;

import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.second_board.second_board.dto.request.SecondBoardCreatRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SecondBoardRepositoryCustom {
    @Transactional
    Page<SecondBoard> findNonDeletedSecondBoard(Pageable pageable);

    @Transactional
    void deleteSecondBoard(Long secondBoardId);

    @Transactional
    void updateSecondBoard(Long secondBoardId, SecondBoardCreatRequestDTO secondBoardCreatRequestDTO);
}
