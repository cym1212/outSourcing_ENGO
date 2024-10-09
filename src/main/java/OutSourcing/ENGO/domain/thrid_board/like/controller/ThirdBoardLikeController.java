package OutSourcing.ENGO.domain.thrid_board.like.controller;


import OutSourcing.ENGO.domain.thrid_board.like.dto.ThirdBoardLikeCountDTO;
import OutSourcing.ENGO.domain.thrid_board.like.service.ThirdBoardLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/third-board")
public class ThirdBoardLikeController {

    private final ThirdBoardLikeService thirdBoardLikeService;

    @Operation(summary = "좋아요 누르기", description = "게시글에 대한 좋아요를 누릅니다.")
    @PostMapping("/{thirdBoardId}/like")
    public ResponseEntity<?> likePost(@PathVariable("thirdBoardId") Long thirdBoardId) {
        thirdBoardLikeService.likePost(thirdBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 눌렀습니다.");
    }

    @Operation(summary = "좋아요 취소", description = "게시글에 대한 좋아요를 취소합니다.")
    @PostMapping("/{thirdBoardId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable("thirdBoardId") Long thirdBoardId) {
        thirdBoardLikeService.unlikePost(thirdBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 취소했습니다.");
    }

    @Operation(summary = "좋아요 갯수 카운트", description = "해당 게시물의 좋아요 갯수를 확인합니다.")
    @GetMapping("/{thirdBoardId}/likes-count")
    public ResponseEntity<ThirdBoardLikeCountDTO> getLikeCount(@PathVariable("thirdBoardId") Long thirdBoardId) {
        ThirdBoardLikeCountDTO likeCount = thirdBoardLikeService.getLikeCount(thirdBoardId);
        return ResponseEntity.ok(likeCount);
    }
}
