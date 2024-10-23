package OutSourcing.ENGO.domain.thrid_board.third_board.service;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardListResponseDTO;
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
import jakarta.transaction.Transactional;
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
                .createdAt(thirdBoard.getCreatedAt())
                .viewCount(thirdBoard.getViewCount())
                .likeCount(thirdBoard.getLikeCount())
                .build());
    }

    public ThirdBoardDetailResponseDTO getThirdBoardDetail(Long thirdBoardId) {
        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        Member currentMember = memberService.getCurrentMember();
        // 유저 한명당 한번씩만 올라가야되면 수정해야함.
        thirdBoard.incrementViewCount();
        thirdBoardRepository.save(thirdBoard);

        return ThirdBoardDetailResponseDTO.builder()
                .id(thirdBoard.getId())
                .authorId(thirdBoard.getMember().getId())
                .title(thirdBoard.getTitle())
                .name(thirdBoard.getMember().getName())
                .createdAt(thirdBoard.getCreatedAt())
                .content(thirdBoard.getContent())
                .likeCount(thirdBoard.getLikeCount())
                .viewCount(thirdBoard.getViewCount())
                .requestUserId(currentMember.getId())
                .requestUserRole(currentMember.getRole())
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
    @Transactional
    public Page<ThirdBoardListResponseDTO> searchBoards(String query, String searchBy, Pageable pageable) {
        Page<ThirdBoard> boards;

        if (searchBy.equals("title")) {
            boards = thirdBoardRepository.findByTitleContainingAndDeletedAtIsNull(query, pageable);
        } else if (searchBy.equals("content")) {
            boards = thirdBoardRepository.findByContentContainingAndDeletedAtIsNull(query, pageable);
        } else if (searchBy.equals("name")) {
            boards = thirdBoardRepository.findByMemberNameContainingAndDeletedAtIsNull(query, pageable);
        } else {
            boards = thirdBoardRepository.findByTitleContainingOrContentContainingOrMemberNameContainingAndDeletedAtIsNull(query, query, query, pageable);
        }

        // Page 객체로 엔티티를 DTO로 변환
        return boards.map(this::convertToDto);
    }

    // 수동 매핑 메서드
    private ThirdBoardListResponseDTO convertToDto(ThirdBoard board) {
        return ThirdBoardListResponseDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .name(board.getMember().getName()) // Member 객체에서 이름 추출
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build();
    }

}
