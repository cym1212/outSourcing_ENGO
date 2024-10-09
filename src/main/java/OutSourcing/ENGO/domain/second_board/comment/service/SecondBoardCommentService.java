package OutSourcing.ENGO.domain.second_board.comment.service;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.repository.Member.MemberRepository;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.domain.second_board.comment.domain.SecondBoardComment;
import OutSourcing.ENGO.domain.second_board.comment.dto.request.SecondBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.second_board.comment.dto.response.SecondBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.second_board.comment.repository.SecondBoardCommentRepository;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.second_board.second_board.repository.SecondBoardRepository;
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
public class SecondBoardCommentService {
    private final SecondBoardCommentRepository secondBoardCommentRepository;
    private final SecondBoardRepository secondBoardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @Transactional(readOnly = true)
    public Page<SecondBoardCommentResponseDTO> getSecondBoardComments(Long secondBoardId, Pageable pageable) {
        SecondBoard secondBoard = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        return secondBoardCommentRepository.findBySecondBoard(secondBoard, pageable)
                .map(comment -> {
                    boolean isDeleted = comment.getDeletedAt() != null;
                    return SecondBoardCommentResponseDTO.builder()
                            .id(comment.getId())
                            .comments(isDeleted ? "삭제된 댓글입니다." : comment.getComments())
                            .memberName(comment.getMember().getName())
                            .boardTitle(comment.getSecondBoard().getTitle())
                            .modifiedAt(comment.getUpdatedAt())
                            .isDeleted(isDeleted)
                            .build();
                });
    }

    @Transactional
    public SecondBoardCommentResponseDTO createSecondBoardComment(Long secondBoardId, SecondBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        SecondBoard secondBoard = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        SecondBoardComment comment = SecondBoardComment.builder()
                .member(member)
                .secondBoard(secondBoard)
                .comments(requestDto.getComments())
                .build();

        SecondBoardComment savedComment = secondBoardCommentRepository.save(comment);

        return SecondBoardCommentResponseDTO.builder()
                .id(savedComment.getId())
                .comments(savedComment.getComments())
                .memberName(member.getName())
                .boardTitle(savedComment.getSecondBoard().getTitle())
                .modifiedAt(savedComment.getUpdatedAt())
                .isDeleted(false)
                .build();
    }

    @Transactional
    public SecondBoardCommentResponseDTO updateSecondBoardComment(Long secondBoardId, Long secondBoardCommentId, SecondBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        SecondBoard secondBoard = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        SecondBoardComment comment = secondBoardCommentRepository.findById(secondBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        secondBoardCommentRepository.updateSecondBoardComment(secondBoardCommentId, requestDto);

        // 업데이트된 댓글 조회 (필요시 다시 조회)
        SecondBoardComment updatedComment = secondBoardCommentRepository.findById(secondBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        //todo comments 왜 변한걸로 안나오는지 확인필요
        return SecondBoardCommentResponseDTO.builder()
                .id(updatedComment.getId())
                .comments(updatedComment.getComments())
                .memberName(member.getName())
                .boardTitle(updatedComment.getSecondBoard().getTitle())
                .modifiedAt(updatedComment.getUpdatedAt())
                .isDeleted(updatedComment.getDeletedAt() != null)
                .build();
    }

    @Transactional
    public void deleteSecondBoardComment(Long secondBoardId, Long secondBoardCommentId) {
        Member member = memberService.getCurrentMember();

        SecondBoard secondBoard = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        SecondBoardComment comment = secondBoardCommentRepository.findById(secondBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }
        secondBoardCommentRepository.deleteSecondBoardComment(secondBoardCommentId);
    }
}
