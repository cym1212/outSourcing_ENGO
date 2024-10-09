package OutSourcing.ENGO.domain.gallery.gallery_board.service;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.request.GalleryBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardListResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.repository.GalleryBoardRepository;
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
public class GalleryBoardService {
    private final GalleryBoardRepository galleryBoardRepository;
    private final MemberService memberService;


    public void createGalleryBoard(GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO) {

        Member currentMember = memberService.getCurrentMember();

        GalleryBoard galleryBoard = GalleryBoard.builder()
                .member(currentMember)
                .title(galleryBoardCreatRequestDTO.getTitle())
                .content(galleryBoardCreatRequestDTO.getContent())
                .build();

        galleryBoardRepository.save(galleryBoard);
    }

    public Page<GalleryBoardListResponseDTO> getAllGalleryBoards(Pageable pageable) {
        Page<GalleryBoard> galleryBoardPage = galleryBoardRepository.findNonDeletedGalleryBoard(pageable);

        return galleryBoardPage.map(galleryBoard -> GalleryBoardListResponseDTO.builder()
                .id(galleryBoard.getId())
                .title(galleryBoard.getTitle())
                .name(galleryBoard.getMember().getName())
                .viewCount(galleryBoard.getViewCount())
                .likeCount(galleryBoard.getLikeCount())
                .build());
    }

    public GalleryBoardDetailResponseDTO getGalleryBoardDetail(Long galleryBoardId) {
        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        // 유저 한명당 한번씩만 올라가야되면 수정해야함.
        galleryBoard.incrementViewCount();
        galleryBoardRepository.save(galleryBoard);

        return GalleryBoardDetailResponseDTO.builder()
                .id(galleryBoard.getId())
                .title(galleryBoard.getTitle())
                .name(galleryBoard.getMember().getName())
                .content(galleryBoard.getContent())
                .likeCount(galleryBoard.getLikeCount())
                .viewCount(galleryBoard.getViewCount())
                .build();
    }

    public void updateGalleryBoard(Long galleryBoardId, GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO) {
        Member currentMember = memberService.getCurrentMember();

        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!galleryBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

       galleryBoardRepository.updateGalleryBoard(galleryBoardId, galleryBoardCreatRequestDTO);
    }


    //물리적 삭제
    public void deleteGalleryBoard(Long galleryBoardId) {
        Member currentMember = memberService.getCurrentMember();

        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!galleryBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        galleryBoardRepository.deleteGalleryBoard(galleryBoard.getId());
    }

}
