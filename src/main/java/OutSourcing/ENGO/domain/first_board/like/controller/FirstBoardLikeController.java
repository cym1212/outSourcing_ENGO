package OutSourcing.ENGO.domain.first_board.like.controller;


import OutSourcing.ENGO.domain.first_board.like.dto.LikeCountDTO;
import OutSourcing.ENGO.domain.first_board.like.service.FirstBoardLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/first-board")
public class FirstBoardLikeController {

    private final FirstBoardLikeService firstBoardLikeService;

    @Operation(summary = "좋아요 누르기", description = "게시글에 대한 좋아요를 누릅니다.")
    @PostMapping("/{firstBoardId}/like")
    public ResponseEntity<?> likePost(@PathVariable("firstBoardId") Long firstBoardId) {
        firstBoardLikeService.likePost(firstBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 눌렀습니다.");
    }

    @Operation(summary = "좋아요 취소", description = "게시글에 대한 좋아요를 취소합니다.")
    @PostMapping("/{firstBoardId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable("firstBoardId") Long firstBoardId) {
        firstBoardLikeService.unlikePost(firstBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 취소했습니다.");
    }

    @Operation(summary = "좋아요 갯수 카운트", description = "해당 게시물의 좋아요 갯수를 확인합니다.")
    @GetMapping("/{firstBoardId}/likes-count")
    public ResponseEntity<LikeCountDTO> getLikeCount(@PathVariable("firstBoardId") Long firstBoardId) {
        LikeCountDTO likeCount = firstBoardLikeService.getLikeCount(firstBoardId);
        return ResponseEntity.ok(likeCount);
    }
}
