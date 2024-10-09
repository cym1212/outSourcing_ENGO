package OutSourcing.ENGO.domain.gallery.like.controller;


import OutSourcing.ENGO.domain.gallery.like.dto.GalleryBoardLikeCountDTO;
import OutSourcing.ENGO.domain.gallery.like.service.GalleryBoardLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery-board")
public class GalleryBoardLikeController {

    private final GalleryBoardLikeService galleryBoardLikeService;

    @Operation(summary = "좋아요 누르기", description = "게시글에 대한 좋아요를 누릅니다.")
    @PostMapping("/{galleryBoardId}/like")
    public ResponseEntity<?> likePost(@PathVariable("galleryBoardId") Long galleryBoardId) {
        galleryBoardLikeService.likePost(galleryBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 눌렀습니다.");
    }

    @Operation(summary = "좋아요 취소", description = "게시글에 대한 좋아요를 취소합니다.")
    @PostMapping("/{galleryBoardId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable("galleryBoardId") Long galleryBoardId) {
        galleryBoardLikeService.unlikePost(galleryBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 취소했습니다.");
    }

    @Operation(summary = "좋아요 갯수 카운트", description = "해당 게시물의 좋아요 갯수를 확인합니다.")
    @GetMapping("/{galleryBoardId}/likes-count")
    public ResponseEntity<GalleryBoardLikeCountDTO> getLikeCount(@PathVariable("galleryBoardId") Long galleryBoardId) {
        GalleryBoardLikeCountDTO likeCount = galleryBoardLikeService.getLikeCount(galleryBoardId);
        return ResponseEntity.ok(likeCount);
    }
}
