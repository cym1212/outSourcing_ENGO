package OutSourcing.ENGO.domain.first_board.first_board.repository;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.first_board.dto.request.FirstBoardCreatRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FirstBoardRepositoryCustom {
    @Transactional
    Page<FirstBoard> findNonDeletedFirstBoard(Pageable pageable);

    @Transactional
    void deleteFirstBoard(Long firstBoardId);

    @Transactional
    void updateFirstBoard(Long firstBoardId, FirstBoardCreatRequestDTO firstBoardCreatRequestDTO);
}
