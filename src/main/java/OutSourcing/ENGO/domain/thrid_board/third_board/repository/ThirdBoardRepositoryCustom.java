package OutSourcing.ENGO.domain.thrid_board.third_board.repository;

import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.dto.request.ThirdBoardCreatRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThirdBoardRepositoryCustom {
    @Transactional
    Page<ThirdBoard> findNonDeletedThirdBoard(Pageable pageable);

    @Transactional
    void deleteThirdBoard(Long thirdBoardId);

    @Transactional
    void updateThirdBoard(Long thirdBoardId, ThirdBoardCreatRequestDTO thirdBoardCreatRequestDTO);
}
