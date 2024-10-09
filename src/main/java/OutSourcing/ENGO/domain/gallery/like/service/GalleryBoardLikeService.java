package OutSourcing.ENGO.domain.gallery.like.service;

import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.repository.GalleryBoardRepository;
import OutSourcing.ENGO.domain.gallery.like.domain.GalleryBoardLike;
import OutSourcing.ENGO.domain.gallery.like.dto.GalleryBoardLikeCountDTO;
import OutSourcing.ENGO.domain.gallery.like.repository.GalleryBoardGalleryBoardLikeRepository;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GalleryBoardLikeService {

    private final GalleryBoardRepository galleryBoardRepository;
    private final GalleryBoardGalleryBoardLikeRepository galleryBoardLikeRepository;
    private final MemberService memberService;

    @Transactional
    public void likePost(Long galleryBoardId) {
        Member member = memberService.getCurrentMember();
        GalleryBoard board = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        if (galleryBoardLikeRepository.existsByGalleryBoardAndMemberQueryDSL(board, member)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        GalleryBoardLike galleryBoardLike = GalleryBoardLike.builder()
                .galleryBoard(board)
                .member(member)
                .build();

        galleryBoardLikeRepository.save(galleryBoardLike);
        board.incrementLikeCount();  // 좋아요 카운트 증가
        galleryBoardRepository.save(board);
    }

    @Transactional
    public void unlikePost(Long galleryBoardIdr) {
        Member member = memberService.getCurrentMember();
        GalleryBoard board = galleryBoardRepository.findById(galleryBoardIdr)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        GalleryBoardLike galleryBoardLike = galleryBoardLikeRepository.findByGalleryBoardAndMember(board, member)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LIKE));

        galleryBoardLikeRepository.delete(galleryBoardLike);
        board.decrementLikeCount();  // 좋아요 카운트 감소
        galleryBoardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public GalleryBoardLikeCountDTO getLikeCount(Long galleryBoardId) {
        GalleryBoard board = galleryBoardRepository.findById(galleryBoardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        long likeCount = galleryBoardLikeRepository.countByGalleryBoardQueryDSL(board);

        return new GalleryBoardLikeCountDTO(likeCount);
    }
}