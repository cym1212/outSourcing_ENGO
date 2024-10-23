package OutSourcing.ENGO.domain.second_board.second_board.service;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardListResponseDTO;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.second_board.second_board.dto.request.SecondBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.second_board.second_board.dto.response.SecondBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.second_board.second_board.dto.response.SecondBoardListResponseDTO;
import OutSourcing.ENGO.domain.second_board.second_board.repository.SecondBoardRepository;
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
public class SecondBoardService {
    private final SecondBoardRepository secondBoardRepository;
    private final MemberService memberService;


    public void createSecondBoard(SecondBoardCreatRequestDTO secondBoardCreatRequestDTO) {

        Member currentMember = memberService.getCurrentMember();

        SecondBoard secondBoard = SecondBoard.builder()
                .member(currentMember)
                .title(secondBoardCreatRequestDTO.getTitle())
                .content(secondBoardCreatRequestDTO.getContent())
                .build();

        secondBoardRepository.save(secondBoard);
    }

    public Page<SecondBoardListResponseDTO> getAllSecondBoards(Pageable pageable) {
        Page<SecondBoard> secondBoardPage = secondBoardRepository.findNonDeletedSecondBoard(pageable);

        return secondBoardPage.map(secondBoard -> SecondBoardListResponseDTO.builder()
                .id(secondBoard.getId())
                .title(secondBoard.getTitle())
                .name(secondBoard.getMember().getName())
                .createdAt(secondBoard.getCreatedAt())
                .viewCount(secondBoard.getViewCount())
                .likeCount(secondBoard.getLikeCount())
                .build());
    }

    public SecondBoardDetailResponseDTO getSecondBoardDetail(Long secondBoardId) {
        SecondBoard secondBoard = secondBoardRepository.findById(secondBoardId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        Member currentMember = memberService.getCurrentMember();
        // 유저 한명당 한번씩만 올라가야되면 수정해야함.
        secondBoard.incrementViewCount();
        secondBoardRepository.save(secondBoard);

        return SecondBoardDetailResponseDTO.builder()
                .id(secondBoard.getId())
                .authorId(secondBoard.getMember().getId())
                .title(secondBoard.getTitle())
                .name(secondBoard.getMember().getName())
                .createdAt(secondBoard.getCreatedAt())
                .content(secondBoard.getContent())
                .likeCount(secondBoard.getLikeCount())
                .viewCount(secondBoard.getViewCount())
                .requestUserId(currentMember.getId())
                .requestUserRole(currentMember.getRole())
                .build();
    }

    public void updateSecondBoard(Long secondBoardId, SecondBoardCreatRequestDTO secondBoardCreatRequestDTO) {
        Member currentMember = memberService.getCurrentMember();

        SecondBoard secondBoard = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!secondBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

       secondBoardRepository.updateSecondBoard(secondBoardId, secondBoardCreatRequestDTO);
    }


    //물리적 삭제
    public void deleteSecondBoard(Long secondBoardId) {
        Member currentMember = memberService.getCurrentMember();

        SecondBoard secondBoard = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!secondBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        secondBoardRepository.deleteSecondBoard(secondBoard.getId());
    }

    @Transactional
    public Page<SecondBoardListResponseDTO> searchBoards(String query, String searchBy, Pageable pageable) {
        Page<SecondBoard> boards;

        if (searchBy.equals("title")) {
            boards = secondBoardRepository.findByTitleContainingAndDeletedAtIsNull(query, pageable);
        } else if (searchBy.equals("content")) {
            boards = secondBoardRepository.findByContentContainingAndDeletedAtIsNull(query, pageable);
        } else if (searchBy.equals("name")) {
            boards = secondBoardRepository.findByMemberNameContainingAndDeletedAtIsNull(query, pageable);
        } else {
            boards = secondBoardRepository.findByTitleContainingOrContentContainingOrMemberNameContainingAndDeletedAtIsNull(query, query, query, pageable);
        }

        // Page 객체로 엔티티를 DTO로 변환
        return boards.map(this::convertToDto);
    }

    // 수동 매핑 메서드
    private SecondBoardListResponseDTO convertToDto(SecondBoard board) {
        return SecondBoardListResponseDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .name(board.getMember().getName()) // Member 객체에서 이름 추출
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build();
    }

}
