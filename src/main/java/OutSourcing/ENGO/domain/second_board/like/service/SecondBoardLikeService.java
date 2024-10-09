package OutSourcing.ENGO.domain.second_board.like.service;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import OutSourcing.ENGO.domain.second_board.second_board.repository.SecondBoardRepository;
import OutSourcing.ENGO.domain.second_board.like.domain.SecondBoardLike;
import OutSourcing.ENGO.domain.second_board.like.dto.SecondBoardLikeCountDTO;
import OutSourcing.ENGO.domain.second_board.like.repository.SecondBoardSecondBoardLikeRepository;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecondBoardLikeService {

    private final SecondBoardRepository secondBoardRepository;
    private final SecondBoardSecondBoardLikeRepository secondBoardLikeRepository;
    private final MemberService memberService;

    @Transactional
    public void likePost(Long secondBoardId) {
        Member member = memberService.getCurrentMember();
        SecondBoard board = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (secondBoardLikeRepository.existsBySecondBoardAndMemberQueryDSL(board, member)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        SecondBoardLike secondBoardLike = SecondBoardLike.builder()
                .secondBoard(board)
                .member(member)
                .build();

        secondBoardLikeRepository.save(secondBoardLike);
        board.incrementLikeCount();  // 좋아요 카운트 증가
        secondBoardRepository.save(board);
    }

    @Transactional
    public void unlikePost(Long secondBoardId) {
        Member member = memberService.getCurrentMember();
        SecondBoard board = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        SecondBoardLike secondBoardLike = secondBoardLikeRepository.findBySecondBoardAndMember(board, member)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LIKE));

        secondBoardLikeRepository.delete(secondBoardLike);
        board.decrementLikeCount();  // 좋아요 카운트 감소
        secondBoardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public SecondBoardLikeCountDTO getLikeCount(Long secondBoardId) {
        SecondBoard board = secondBoardRepository.findById(secondBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        long likeCount = secondBoardLikeRepository.countBySecondBoardQueryDSL(board);

        return new SecondBoardLikeCountDTO(likeCount);
    }
}