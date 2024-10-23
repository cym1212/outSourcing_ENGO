package OutSourcing.ENGO.domain.gallery.gallery_board.service;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryImage;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.request.GalleryBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardListResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_board.GalleryBoardRepository;
import OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_image.GalleryImageRepository;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.enums.Role;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import OutSourcing.ENGO.infra.s3upload.service.S3UploadService;
import OutSourcing.ENGO.infra.s3upload.utils.UploadUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class GalleryBoardService {
    private final GalleryBoardRepository galleryBoardRepository;
    private final MemberService memberService;
    private final S3UploadService s3UploadService;
    private final GalleryImageRepository galleryImageRepository;

    @Transactional
    public void createGalleryBoard(GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO) {

        Member currentMember = memberService.getCurrentMember();

        GalleryBoard galleryBoard = GalleryBoard.builder()
                .member(currentMember)
                .title(galleryBoardCreatRequestDTO.getTitle())
                .content(galleryBoardCreatRequestDTO.getContent())
                .build();

        galleryBoardRepository.save(galleryBoard);
        if (galleryBoardCreatRequestDTO.getImages() == null || galleryBoardCreatRequestDTO.getImages().isEmpty()) {
            log.info("이미지가 없으므로 그대로 진행합니다");
        } else {
            for (MultipartFile image : galleryBoardCreatRequestDTO.getImages()) {
                String fileName = image.getOriginalFilename();
                String imageUrl = s3UploadService.uploadFile(image, UploadUtils.GALLERY_IMAGE, currentMember.getId());
                GalleryImage galleryImage = GalleryImage.builder()
                        .galleryBoard(galleryBoard)
                        .imageUrl(imageUrl)
                        .fileName(fileName)
                        .build();
                galleryImageRepository.save(galleryImage);
            }
        }

    }

    @Transactional
    public Page<GalleryBoardListResponseDTO> getAllGalleryBoards(Pageable pageable) {
        Page<GalleryBoard> galleryBoardPage = galleryBoardRepository.findNonDeletedGalleryBoard(pageable);

        return galleryBoardPage.map(galleryBoard -> GalleryBoardListResponseDTO.builder()
                .id(galleryBoard.getId())
                .title(galleryBoard.getTitle())
                .name(galleryBoard.getMember().getName())
                .likeCount(galleryBoard.getLikeCount())
                .viewCount(galleryBoard.getViewCount())
                .createdAt(galleryBoard.getCreatedAt())
                .build());
    }

    @Transactional
    public GalleryBoardDetailResponseDTO getGalleryBoardDetail(Long galleryBoardId) {
        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        Member currentMember = memberService.getCurrentMember();
        // 유저 한명당 한번씩만 올라가야되면 수정해야함.
        galleryBoard.incrementViewCount();
        galleryBoardRepository.save(galleryBoard);

        List<String> imageUrls = galleryImageRepository.findByGalleryBoardAndDeletedAtIsNull(galleryBoard).stream()
                .map(GalleryImage::getImageUrl)
                .toList();

        return GalleryBoardDetailResponseDTO.builder()
                .id(galleryBoard.getId())
                .authorId(galleryBoard.getMember().getId())
                .title(galleryBoard.getTitle())
                .name(galleryBoard.getMember().getName())
                .content(galleryBoard.getContent())
                .likeCount(galleryBoard.getLikeCount())
                .viewCount(galleryBoard.getViewCount())
                .imageUrls(imageUrls)
                .createdAt(galleryBoard.getCreatedAt())
                .requestUserId(currentMember.getId())
                .requestUserRole(currentMember.getRole())
                .build();
    }

    @Transactional
    public void updateGalleryBoard(Long galleryBoardId, GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO) {
        Member currentMember = memberService.getCurrentMember();

        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!galleryBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        if (galleryBoardCreatRequestDTO.getImages() == null || galleryBoardCreatRequestDTO.getImages().isEmpty()) {
            log.info("이미지가 없으므로 그대로 진행합니다");
        } else {
            List<GalleryImage> existingImages = galleryImageRepository.findByGalleryBoardAndDeletedAtIsNull(galleryBoard);
            for (GalleryImage image : existingImages) {
                String deletedImageKey = s3UploadService.copyFile(image.getImageUrl(), UploadUtils.GALLERY_IMAGE, UploadUtils.GALLERY_IMAGE_DELETED);
                s3UploadService.deleteFile(image.getImageUrl());  // 원본 삭제
            }

            galleryImageRepository.deleteGalleryImages(galleryBoardId);

            // 새로운 이미지 업로드
            for (MultipartFile image : galleryBoardCreatRequestDTO.getImages()) {
                String fileName = image.getOriginalFilename();
                String imageUrl = s3UploadService.uploadFile(image, UploadUtils.GALLERY_IMAGE, currentMember.getId());

                GalleryImage galleryImage = GalleryImage.builder()
                        .galleryBoard(galleryBoard)
                        .imageUrl(imageUrl)
                        .fileName(fileName)
                        .build();

                galleryImageRepository.save(galleryImage);
            }

        }


        galleryBoardRepository.updateGalleryBoard(galleryBoardId, galleryBoardCreatRequestDTO);
    }


    @Transactional
    public void deleteGalleryBoard(Long galleryBoardId) {
        Member currentMember = memberService.getCurrentMember();

        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (!galleryBoard.getMember().equals(currentMember) && !currentMember.getRole().equals(Role.ADMIN)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        List<GalleryImage> images = galleryImageRepository.findByGalleryBoardAndDeletedAtIsNull(galleryBoard);
        for (GalleryImage image : images) {
            String deletedImageKey = s3UploadService.copyFile(image.getImageUrl(), UploadUtils.GALLERY_IMAGE, UploadUtils.GALLERY_IMAGE_DELETED);
            s3UploadService.deleteFile(image.getImageUrl());  // 원본 삭제
            galleryImageRepository.delete(image);  // 데이터베이스에서도 삭제
        }

        galleryBoardRepository.deleteGalleryBoard(galleryBoard.getId());
    }

    @Transactional
    public Page<GalleryBoardListResponseDTO> searchBoards(String query, String searchBy, Pageable pageable) {
        Page<GalleryBoard> boards;

        if (searchBy.equals("title")) {
            boards = galleryBoardRepository.findByTitleContainingAndDeletedAtIsNull(query, pageable);
        } else if (searchBy.equals("content")) {
            boards = galleryBoardRepository.findByContentContainingAndDeletedAtIsNull(query, pageable);
        } else if (searchBy.equals("name")) {
            boards = galleryBoardRepository.findByMemberNameContainingAndDeletedAtIsNull(query, pageable);
        } else {
            boards = galleryBoardRepository.findByTitleContainingOrContentContainingOrMemberNameContainingAndDeletedAtIsNull(query, query, query, pageable);
        }

        // Page 객체로 엔티티를 DTO로 변환
        return boards.map(this::convertToDto);
    }

    // 수동 매핑 메서드
    private GalleryBoardListResponseDTO convertToDto(GalleryBoard board) {
        return GalleryBoardListResponseDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .name(board.getMember().getName()) // Member 객체에서 이름 추출
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build();
    }

}
