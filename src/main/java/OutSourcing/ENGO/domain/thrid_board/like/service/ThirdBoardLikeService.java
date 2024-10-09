package OutSourcing.ENGO.domain.thrid_board.like.service;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import OutSourcing.ENGO.domain.thrid_board.third_board.repository.ThirdBoardRepository;
import OutSourcing.ENGO.domain.thrid_board.like.domain.ThirdBoardLike;
import OutSourcing.ENGO.domain.thrid_board.like.dto.ThirdBoardLikeCountDTO;
import OutSourcing.ENGO.domain.thrid_board.like.repository.ThirdBoardThirdBoardLikeRepository;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThirdBoardLikeService {

    private final ThirdBoardRepository thirdBoardRepository;
    private final ThirdBoardThirdBoardLikeRepository thirdBoardLikeRepository;
    private final MemberService memberService;

    @Transactional
    public void likePost(Long thirdBoardId) {
        Member member = memberService.getCurrentMember();
        ThirdBoard board = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (thirdBoardLikeRepository.existsByThirdBoardAndMemberQueryDSL(board, member)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        ThirdBoardLike thirdBoardLike = ThirdBoardLike.builder()
                .thirdBoard(board)
                .member(member)
                .build();

        thirdBoardLikeRepository.save(thirdBoardLike);
        board.incrementLikeCount();  // 좋아요 카운트 증가
        thirdBoardRepository.save(board);
    }

    @Transactional
    public void unlikePost(Long thirdBoardId) {
        Member member = memberService.getCurrentMember();
        ThirdBoard board = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        ThirdBoardLike thirdBoardLike = thirdBoardLikeRepository.findByThirdBoardAndMember(board, member)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LIKE));

        thirdBoardLikeRepository.delete(thirdBoardLike);
        board.decrementLikeCount();  // 좋아요 카운트 감소
        thirdBoardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public ThirdBoardLikeCountDTO getLikeCount(Long thirdBoardId) {
        ThirdBoard board = thirdBoardRepository.findById(thirdBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        long likeCount = thirdBoardLikeRepository.countByThirdBoardQueryDSL(board);

        return new ThirdBoardLikeCountDTO(likeCount);
    }
}