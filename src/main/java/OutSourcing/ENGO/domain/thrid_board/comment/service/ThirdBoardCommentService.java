package OutSourcing.ENGO.domain.thrid_board.comment.service;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.repository.Member.MemberRepository;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.domain.thrid_board.comment.domain.ThirdBoardComment;
import OutSourcing.ENGO.domain.thrid_board.comment.dto.request.ThirdBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.thrid_board.comment.dto.response.ThirdBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.thrid_board.comment.repository.ThirdBoardCommentRepository;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.repository.ThirdBoardRepository;
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
public class ThirdBoardCommentService {
    private final ThirdBoardCommentRepository thirdBoardCommentRepository;
    private final ThirdBoardRepository thirdBoardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @Transactional(readOnly = true)
    public Page<ThirdBoardCommentResponseDTO> getThirdBoardComments(Long thirdBoardId, Pageable pageable) {
        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        return thirdBoardCommentRepository.findByThirdBoard(thirdBoard, pageable)
                .map(comment -> {
                    boolean isDeleted = comment.getDeletedAt() != null;
                    return ThirdBoardCommentResponseDTO.builder()
                            .id(comment.getId())
                            .comments(isDeleted ? "삭제된 댓글입니다." : comment.getComments())
                            .memberName(comment.getMember().getName())
                            .boardTitle(comment.getThirdBoard().getTitle())
                            .modifiedAt(comment.getUpdatedAt())
                            .isDeleted(isDeleted)
                            .build();
                });
    }

    @Transactional
    public ThirdBoardCommentResponseDTO createThirdBoardComment(Long thirdBoardId, ThirdBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        ThirdBoardComment comment = ThirdBoardComment.builder()
                .member(member)
                .thirdBoard(thirdBoard)
                .comments(requestDto.getComments())
                .build();

        ThirdBoardComment savedComment = thirdBoardCommentRepository.save(comment);

        return ThirdBoardCommentResponseDTO.builder()
                .id(savedComment.getId())
                .comments(savedComment.getComments())
                .memberName(member.getName())
                .boardTitle(savedComment.getThirdBoard().getTitle())
                .modifiedAt(savedComment.getUpdatedAt())
                .isDeleted(false)
                .build();
    }

    @Transactional
    public ThirdBoardCommentResponseDTO updateThirdBoardComment(Long thirdBoardId, Long thirdBoardCommentId, ThirdBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        ThirdBoardComment comment = thirdBoardCommentRepository.findById(thirdBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        thirdBoardCommentRepository.updateThirdBoardComment(thirdBoardCommentId, requestDto);

        // 업데이트된 댓글 조회 (필요시 다시 조회)
        ThirdBoardComment updatedComment = thirdBoardCommentRepository.findById(thirdBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        //todo comments 왜 변한걸로 안나오는지 확인필요
        return ThirdBoardCommentResponseDTO.builder()
                .id(updatedComment.getId())
                .comments(updatedComment.getComments())
                .memberName(member.getName())
                .boardTitle(updatedComment.getThirdBoard().getTitle())
                .modifiedAt(updatedComment.getUpdatedAt())
                .isDeleted(updatedComment.getDeletedAt() != null)
                .build();
    }

    @Transactional
    public void deleteThirdBoardComment(Long thirdBoardId, Long thirdBoardCommentId) {
        Member member = memberService.getCurrentMember();

        ThirdBoard thirdBoard = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        ThirdBoardComment comment = thirdBoardCommentRepository.findById(thirdBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }
        thirdBoardCommentRepository.deleteThirdBoardComment(thirdBoardCommentId);
    }
}
