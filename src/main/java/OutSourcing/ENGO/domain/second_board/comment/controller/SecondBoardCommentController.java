package OutSourcing.ENGO.domain.second_board.comment.controller;


import OutSourcing.ENGO.domain.second_board.comment.dto.request.SecondBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.second_board.comment.dto.response.SecondBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.second_board.comment.service.SecondBoardCommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/second-board/{secondBoardId}/second-board-comments")
public class SecondBoardCommentController {

    @Autowired
    private SecondBoardCommentService secondBoardCommentService;

    @GetMapping
    @Operation(summary = "게시판 댓글 조회", description = "해당 게시판에 속해있는 댓글들을 페이징하여 조회합니다.")
    public ResponseEntity<Page<SecondBoardCommentResponseDTO>> getSecondBoardComments(@PathVariable("secondBoardId") Long secondBoardId) {
        Page<SecondBoardCommentResponseDTO> comments = secondBoardCommentService.getSecondBoardComments(secondBoardId, Pageable.ofSize(10));
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/create")
    @Operation(summary = "게시판 댓글 저장", description = "새로운 댓글을 저장합니다.")
    public ResponseEntity<SecondBoardCommentResponseDTO> createSecondBoardComment(@PathVariable("secondBoardId") Long secondBoardId, @RequestBody SecondBoardCommentRequestDTO secondBoardCommentRequestDTO) {
        SecondBoardCommentResponseDTO responseDto = secondBoardCommentService.createSecondBoardComment(secondBoardId, secondBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/update/{secondBoardCommentId}")
    @Operation(summary = "게시판 댓글 수정", description = "작성한 댓글을 수정합니다. (작성자만 가능)")
    public ResponseEntity<SecondBoardCommentResponseDTO> updateSecondBoardComment(@PathVariable("secondBoardId") Long secondBoardId, @PathVariable("secondBoardCommentId") Long secondBoardCommentId, @RequestBody SecondBoardCommentRequestDTO secondBoardCommentRequestDTO) {
        SecondBoardCommentResponseDTO responseDto = secondBoardCommentService.updateSecondBoardComment(secondBoardId, secondBoardCommentId, secondBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/delete/{secondBoardCommentId}")
    @Operation(summary = "게시판 댓글 삭제", description = "작성한 댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteSecondBoardComment(@PathVariable("secondBoardId") Long secondBoardId, @PathVariable("secondBoardCommentId") Long secondBoardCommentId) {
        secondBoardCommentService.deleteSecondBoardComment(secondBoardId, secondBoardCommentId);
        return ResponseEntity.noContent().build();
    }


}
