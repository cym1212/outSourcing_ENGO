package OutSourcing.ENGO.domain.first_board.comment.service;

import OutSourcing.ENGO.domain.first_board.comment.domain.FirstBoardComment;
import OutSourcing.ENGO.domain.first_board.comment.dto.request.FirstBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.first_board.comment.dto.response.FirstBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.first_board.comment.repository.FirstBoardCommentRepository;
import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.first_board.repository.FirstBoardRepository;
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
public class FirstBoardCommentService {
    private final FirstBoardCommentRepository firstBoardCommentRepository;
    private final FirstBoardRepository firstBoardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @Transactional(readOnly = true)
    public Page<FirstBoardCommentResponseDTO> getFirstBoardComments(Long firstBoardId, Pageable pageable) {
        FirstBoard firstBoard = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        return firstBoardCommentRepository.findByFirstBoard(firstBoard, pageable)
                .map(comment -> {
                    boolean isDeleted = comment.getDeletedAt() != null;
                    return FirstBoardCommentResponseDTO.builder()
                            .id(comment.getId())
                            .comments(isDeleted ? "삭제된 댓글입니다." : comment.getComments())
                            .memberName(comment.getMember().getName())
                            .boardTitle(comment.getFirstBoard().getTitle())
                            .modifiedAt(comment.getUpdatedAt())
                            .isDeleted(isDeleted)
                            .build();
                });
    }

    @Transactional
    public FirstBoardCommentResponseDTO createFirstBoardComment(Long firstBoardId, FirstBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        FirstBoard firstBoard = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        FirstBoardComment comment = FirstBoardComment.builder()
                .member(member)
                .firstBoard(firstBoard)
                .comments(requestDto.getComments())
                .build();

        FirstBoardComment savedComment = firstBoardCommentRepository.save(comment);

        return FirstBoardCommentResponseDTO.builder()
                .id(savedComment.getId())
                .comments(savedComment.getComments())
                .memberName(member.getName())
                .boardTitle(savedComment.getFirstBoard().getTitle())
                .modifiedAt(savedComment.getUpdatedAt())
                .isDeleted(false)
                .build();
    }

    @Transactional
    public FirstBoardCommentResponseDTO updateFirstBoardComment(Long firstBoardId, Long firstBoardCommentId, FirstBoardCommentRequestDTO requestDto) {
        Member member = memberService.getCurrentMember();

        FirstBoard firstBoard = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        FirstBoardComment comment = firstBoardCommentRepository.findById(firstBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        firstBoardCommentRepository.updateFirstBoardComment(firstBoardCommentId, requestDto);

        // 업데이트된 댓글 조회 (필요시 다시 조회)
        FirstBoardComment updatedComment = firstBoardCommentRepository.findById(firstBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        //todo comments 왜 변한걸로 안나오는지 확인필요
        return FirstBoardCommentResponseDTO.builder()
                .id(updatedComment.getId())
                .comments(updatedComment.getComments())
                .memberName(member.getName())
                .boardTitle(updatedComment.getFirstBoard().getTitle())
                .modifiedAt(updatedComment.getUpdatedAt())
                .isDeleted(updatedComment.getDeletedAt() != null)
                .build();
    }

    @Transactional
    public void deleteFirstBoardComment(Long firstBoardId, Long firstBoardCommentId) {
        Member member = memberService.getCurrentMember();

        FirstBoard firstBoard = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        FirstBoardComment comment = firstBoardCommentRepository.findById(firstBoardCommentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getMember().equals(member) && !member.getRole().equals(Role.ADMIN)) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }
        firstBoardCommentRepository.deleteFirstBoardComment(firstBoardCommentId);
    }
}
