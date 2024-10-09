package OutSourcing.ENGO.domain.first_board.first_board.service;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.first_board.dto.request.FirstBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.first_board.first_board.dto.response.FirstBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.first_board.first_board.dto.response.FirstBoardListResponseDTO;
import OutSourcing.ENGO.domain.first_board.first_board.repository.FirstBoardRepository;
import OutSourcing.ENGO.domain.member.domain.Member;


import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.enums.Role;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirstBoardService {
    private final FirstBoardRepository firstBoardRepository;
    private final MemberService memberService;


    public void createFirstBoard(FirstBoardCreatRequestDTO firstBoardCreatRequestDTO) {

        Member currentMember = memberService.getCurrentMember();

        FirstBoard firstBoard = FirstBoard.builder()
                .member(currentMember)
                .title(firstBoardCreatRequestDTO.getTitle())
                .content(firstBoardCreatRequestDTO.getContent())
                .build();

        firstBoardRepository.save(firstBoard);
    }

    public Page<FirstBoardListResponseDTO> getAllFirstBoards(Pageable pageable) {
        Page<FirstBoard> firstBoardPage = firstBoardRepository.findNonDeletedFirstBoard(pageable);

        return firstBoardPage.map(firstBoard -> FirstBoardListResponseDTO.builder()
                .id(firstBoard.getId())
                .title(firstBoard.getTitle())
                .name(firstBoard.getMember().getName())
                .createdAt(firstBoard.getCreatedAt())
                .viewCount(firstBoard.getViewCount())
                .likeCount(firstBoard.getLikeCount())
                .build());
    }

    public FirstBoardDetailResponseDTO getFirstBoardDetail(Long firstBoardId) {
        FirstBoard firstBoard = firstBoardRepository.findById(firstBoardId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        // 유저 한명당 한번씩만 올라가야되면 수정해야함.
        firstBoard.incrementViewCount();
        firstBoardRepository.save(firstBoard);

        return FirstBoardDetailResponseDTO.builder()
                .id(firstBoard.getId())
                .title(firstBoard.getTitle())
                .name(firstBoard.getMember().getName())
                .createdAt(firstBoard.getCreatedAt())
                .content(firstBoard.getContent())
                .likeCount(firstBoard.getLikeCount())
                .viewCount(firstBoard.getViewCount())
                .build();
    }

    public void updateFirstBoard(Long firstBoardId, FirstBoardCreatRequestDTO firstBoardCreatRequestDTO) {
        Member currentMember = memberService.getCurrentMember();

        FirstBoard firstBoard = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!firstBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

       firstBoardRepository.updateFirstBoard(firstBoardId, firstBoardCreatRequestDTO);
    }


    //물리적 삭제
    public void deleteFirstBoard(Long firstBoardId) {
        Member currentMember = memberService.getCurrentMember();

        FirstBoard firstBoard = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!firstBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        firstBoardRepository.deleteFirstBoard(firstBoard.getId());
    }

}
