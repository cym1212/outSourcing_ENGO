package OutSourcing.ENGO.domain.second_board.like.controller;


import OutSourcing.ENGO.domain.second_board.like.dto.SecondBoardLikeCountDTO;
import OutSourcing.ENGO.domain.second_board.like.service.SecondBoardLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/second-board")
public class SecondBoardLikeController {

    private final SecondBoardLikeService secondBoardLikeService;

    @Operation(summary = "좋아요 누르기", description = "게시글에 대한 좋아요를 누릅니다.")
    @PostMapping("/{secondBoardId}/like")
    public ResponseEntity<?> likePost(@PathVariable("secondBoardId") Long secondBoardId) {
        secondBoardLikeService.likePost(secondBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 눌렀습니다.");
    }

    @Operation(summary = "좋아요 취소", description = "게시글에 대한 좋아요를 취소합니다.")
    @PostMapping("/{secondBoardId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable("secondBoardId") Long secondBoardId) {
        secondBoardLikeService.unlikePost(secondBoardId);
        return ResponseEntity.ok("게시글에 좋아요를 취소했습니다.");
    }

    @Operation(summary = "좋아요 갯수 카운트", description = "해당 게시물의 좋아요 갯수를 확인합니다.")
    @GetMapping("/{secondBoardId}/likes-count")
    public ResponseEntity<SecondBoardLikeCountDTO> getLikeCount(@PathVariable("secondBoardId") Long secondBoardId) {
        SecondBoardLikeCountDTO likeCount = secondBoardLikeService.getLikeCount(secondBoardId);
        return ResponseEntity.ok(likeCount);
    }
}
