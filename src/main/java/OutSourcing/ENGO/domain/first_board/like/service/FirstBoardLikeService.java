package OutSourcing.ENGO.domain.first_board.like.service;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.first_board.repository.FirstBoardRepository;
import OutSourcing.ENGO.domain.first_board.like.domain.Like;
import OutSourcing.ENGO.domain.first_board.like.dto.LikeCountDTO;
import OutSourcing.ENGO.domain.first_board.like.repository.LikeRepository;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirstBoardLikeService {

    private final FirstBoardRepository firstBoardRepository;
    private final LikeRepository likeRepository;
    private final MemberService memberService;

    @Transactional
    public void likePost(Long firstBoardId) {
        Member member = memberService.getCurrentMember();
        FirstBoard board = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (likeRepository.existsByFirstBoardAndMemberQueryDSL(board, member)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        Like like = Like.builder()
                .firstBoard(board)
                .member(member)
                .build();

        likeRepository.save(like);
        board.incrementLikeCount();  // 좋아요 카운트 증가
        firstBoardRepository.save(board);
    }

    @Transactional
    public void unlikePost(Long firstBoardIdr) {
        Member member = memberService.getCurrentMember();
        FirstBoard board = firstBoardRepository.findById(firstBoardIdr)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        Like like = likeRepository.findByFirstBoardAndMember(board, member)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LIKE));

        likeRepository.delete(like);
        board.decrementLikeCount();  // 좋아요 카운트 감소
        firstBoardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public LikeCountDTO getLikeCount(Long firstBoardId) {
        FirstBoard board = firstBoardRepository.findById(firstBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        long likeCount = likeRepository.countByFirstBoardQueryDSL(board);

        return new LikeCountDTO(likeCount);
    }
}