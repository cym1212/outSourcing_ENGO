package OutSourcing.ENGO.domain.thrid_board.comment.controller;


import OutSourcing.ENGO.domain.thrid_board.comment.dto.request.ThirdBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.thrid_board.comment.dto.response.ThirdBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.thrid_board.comment.service.ThirdBoardCommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/third-board/{thirdBoardId}/third-board-comments")
public class ThirdBoardCommentController {

    @Autowired
    private ThirdBoardCommentService thirdBoardCommentService;

    @GetMapping
    @Operation(summary = "게시판 댓글 조회", description = "해당 게시판에 속해있는 댓글들을 페이징하여 조회합니다.")
    public ResponseEntity<Page<ThirdBoardCommentResponseDTO>> getThirdBoardComments(@PathVariable("thirdBoardId") Long thirdBoardId) {
        Page<ThirdBoardCommentResponseDTO> comments = thirdBoardCommentService.getThirdBoardComments(thirdBoardId, Pageable.ofSize(10));
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/create")
    @Operation(summary = "게시판 댓글 저장", description = "새로운 댓글을 저장합니다.")
    public ResponseEntity<ThirdBoardCommentResponseDTO> createThirdBoardComment(@PathVariable("thirdBoardId") Long thirdBoardId, @RequestBody ThirdBoardCommentRequestDTO thirdBoardCommentRequestDTO) {
        ThirdBoardCommentResponseDTO responseDto = thirdBoardCommentService.createThirdBoardComment(thirdBoardId, thirdBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/update/{thirdBoardCommentId}")
    @Operation(summary = "게시판 댓글 수정", description = "작성한 댓글을 수정합니다. (작성자만 가능)")
    public ResponseEntity<ThirdBoardCommentResponseDTO> updateThirdBoardComment(@PathVariable("thirdBoardId") Long thirdBoardId, @PathVariable("thirdBoardCommentId") Long thirdBoardCommentId, @RequestBody ThirdBoardCommentRequestDTO thirdBoardCommentRequestDTO) {
        ThirdBoardCommentResponseDTO responseDto = thirdBoardCommentService.updateThirdBoardComment(thirdBoardId, thirdBoardCommentId, thirdBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/delete/{thirdBoardCommentId}")
    @Operation(summary = "게시판 댓글 삭제", description = "작성한 댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteThirdBoardComment(@PathVariable("thirdBoardId") Long thirdBoardId, @PathVariable("thirdBoardCommentId") Long thirdBoardCommentId) {
        thirdBoardCommentService.deleteThirdBoardComment(thirdBoardId, thirdBoardCommentId);
        return ResponseEntity.noContent().build();
    }


}
