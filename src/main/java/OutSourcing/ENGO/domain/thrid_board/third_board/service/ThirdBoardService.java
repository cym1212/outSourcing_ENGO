package OutSourcing.ENGO.domain.thrid_board.third_board.service;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.dto.request.ThirdBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.thrid_board.third_board.dto.response.ThirdBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.thrid_board.third_board.dto.response.ThirdBoardListResponseDTO;
import OutSourcing.ENGO.domain.thrid_board.third_board.repository.ThirdBoardRepository;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.enums.Role;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThirdBoardService {
    private final ThirdBoardRepository thirdBoardRepository;
    private final MemberService memberService;


    public void createThirdBoard(ThirdBoardCreatRequestDTO thirdBoardCreatRequestDTO) {

        Member currentMember = memberService.getCurrentMember();

        ThirdBoard thirdBoard = ThirdBoard.builder()
                .member(currentMember)
                .title(thirdBoardCreatRequestDTO.getTitle())
                .content(thirdBoardCreatRequestDTO.getContent())
                .build();

        thirdBoardRepository.save(thirdBoard);
    }

    public Page<ThirdBoardListResponseDTO> getAllThirdBoards(Pageable pageable) {
        Page<ThirdBoard> thirdBoardPage = thirdBoardRepository.findNonDeletedThirdBoard(pageable);

        return thirdBoardPage.map(thirdBoard -> ThirdBoardListResponseDTO.builder()
                .id(thirdBoard.getId())
                .title(thirdBoard.getTitle())
                .name(thirdBoard.getMember().getName())
                .viewCount(thirdBoard.getViewCount())
                .likeCount(thirdBoard.getLikeCount())
                .build());
    }

    public ThirdBoardDetailResponseDTO getThirdBoardDetail(Long thirdBoardId) {
        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        // 유저 한명당 한번씩만 올라가야되면 수정해야함.
        thirdBoard.incrementViewCount();
        thirdBoardRepository.save(thirdBoard);

        return ThirdBoardDetailResponseDTO.builder()
                .id(thirdBoard.getId())
                .title(thirdBoard.getTitle())
                .name(thirdBoard.getMember().getName())
                .content(thirdBoard.getContent())
                .likeCount(thirdBoard.getLikeCount())
                .viewCount(thirdBoard.getViewCount())
                .build();
    }

    public void updateThirdBoard(Long thirdBoardId, ThirdBoardCreatRequestDTO thirdBoardCreatRequestDTO) {
        Member currentMember = memberService.getCurrentMember();

        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!thirdBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

       thirdBoardRepository.updateThirdBoard(thirdBoardId, thirdBoardCreatRequestDTO);
    }


    //물리적 삭제
    public void deleteThirdBoard(Long thirdBoardId) {
        Member currentMember = memberService.getCurrentMember();

        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!thirdBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        thirdBoardRepository.deleteThirdBoard(thirdBoard.getId());
    }

}
