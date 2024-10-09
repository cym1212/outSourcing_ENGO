package OutSourcing.ENGO.domain.gallery.comment.service;

import OutSourcing.ENGO.domain.gallery.comment.domain.GalleryBoardComment;
import OutSourcing.ENGO.domain.gallery.comment.dto.request.GalleryBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.gallery.comment.dto.response.GalleryBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.gallery.comment.repository.GalleryBoardCommentRepository;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.repository.gallery_board.GalleryBoardRepository;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.repository.Member.MemberRepository;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.enums.Role;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GalleryBoardCommentService {
    private final GalleryBoardCommentRepository galleryBoardCommentRepository;
    private final GalleryBoardRepository galleryBoardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @Transactional(readOnly = true)
    public Page<GalleryBoardCommentResponseDTO> getGalleryBoardComments(Long galleryBoardId, Pageable pageable) {
        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        return galleryBoardCommentRepository.findByGalleryBoard(galleryBoard, pageable)
                .map(comment -> {
                    boolean isDeleted = comment.getDeletedAt() != null;
                    return GalleryBoardCommentResponseDTO.builder()
                            .id(comment.getId())
                            .comments(isDeleted ? "삭제된 댓글입니다." : comment.getComments())
                            .memberName(comment.getMember().getName())
                            .boardTitle(comment.getGalleryBoard().getTitle())
                            .modifiedAt(comment.getUpdatedAt())
                            .isDeleted(isDeleted)
                            .build();
                });
    }

    @Transactional
    public GalleryBoardCommentResponseDTO createGalleryBoardComment(Long galleryBoardId, GalleryBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        GalleryBoardComment comment = GalleryBoardComment.builder()
                .member(member)
                .galleryBoard(galleryBoard)
                .comments(requestDto.getComments())
                .build();

        GalleryBoardComment savedComment = galleryBoardCommentRepository.save(comment);

        return GalleryBoardCommentResponseDTO.builder()
                .id(savedComment.getId())
                .comments(savedComment.getComments())
                .memberName(member.getName())
                .boardTitle(savedComment.getGalleryBoard().getTitle())
                .modifiedAt(savedComment.getUpdatedAt())
                .isDeleted(false)
                .build();
    }

    @Transactional
    public GalleryBoardCommentResponseDTO updateGalleryBoardComment(Long galleryBoardId, Long galleryBoardCommentId, GalleryBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        GalleryBoardComment comment = galleryBoardCommentRepository.findById(galleryBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        galleryBoardCommentRepository.updateGalleryBoardComment(galleryBoardCommentId, requestDto);

        // 업데이트된 댓글 조회 (필요시 다시 조회)
        GalleryBoardComment updatedComment = galleryBoardCommentRepository.findById(galleryBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        //todo comments 왜 변한걸로 안나오는지 확인필요
        return GalleryBoardCommentResponseDTO.builder()
                .id(updatedComment.getId())
                .comments(updatedComment.getComments())
                .memberName(member.getName())
                .boardTitle(updatedComment.getGalleryBoard().getTitle())
                .modifiedAt(updatedComment.getUpdatedAt())
                .isDeleted(updatedComment.getDeletedAt() != null)
                .build();
    }

    @Transactional
    public void deleteGalleryBoardComment(Long galleryBoardId, Long galleryBoardCommentId) {
        Member member = memberService.getCurrentMember();

        GalleryBoard galleryBoard = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        GalleryBoardComment comment = galleryBoardCommentRepository.findById(galleryBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }
        galleryBoardCommentRepository.deleteGalleryBoardComment(galleryBoardCommentId);
    }
}
