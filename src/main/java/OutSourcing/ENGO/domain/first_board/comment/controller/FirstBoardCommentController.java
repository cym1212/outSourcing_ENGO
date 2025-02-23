package OutSourcing.ENGO.domain.first_board.comment.controller;


import OutSourcing.ENGO.domain.first_board.comment.dto.request.FirstBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.first_board.comment.dto.response.FirstBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.first_board.comment.service.FirstBoardCommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/first-board/{firstBoardId}/first-board-comments")
@RequiredArgsConstructor
public class FirstBoardCommentController {

    private final FirstBoardCommentService firstBoardCommentService;

    @GetMapping
    @Operation(summary = "게시판 댓글 조회", description = "해당 게시판에 속해있는 댓글들을 페이징하여 조회합니다.")
    public ResponseEntity<Page<FirstBoardCommentResponseDTO>> getFirstBoardComments(@PathVariable("firstBoardId") Long firstBoardId) {
        Page<FirstBoardCommentResponseDTO> comments = firstBoardCommentService.getFirstBoardComments(firstBoardId, Pageable.ofSize(10));
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/create")
    @Operation(summary = "게시판 댓글 저장", description = "새로운 댓글을 저장합니다.")
    public ResponseEntity<FirstBoardCommentResponseDTO> createFirstBoardComment(@PathVariable("firstBoardId") Long firstBoardId, @RequestBody FirstBoardCommentRequestDTO firstBoardCommentRequestDTO) {
        FirstBoardCommentResponseDTO responseDto = firstBoardCommentService.createFirstBoardComment(firstBoardId, firstBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/update/{firstBoardCommentId}")
    @Operation(summary = "게시판 댓글 수정", description = "작성한 댓글을 수정합니다. (작성자만 가능)")
    public ResponseEntity<FirstBoardCommentResponseDTO> updateFirstBoardComment(@PathVariable("firstBoardId") Long firstBoardId, @PathVariable("firstBoardCommentId") Long firstBoardCommentId, @RequestBody FirstBoardCommentRequestDTO firstBoardCommentRequestDTO) {
        FirstBoardCommentResponseDTO responseDto = firstBoardCommentService.updateFirstBoardComment(firstBoardId, firstBoardCommentId, firstBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/delete/{firstBoardCommentId}")
    @Operation(summary = "게시판 댓글 삭제", description = "작성한 댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteFirstBoardComment(@PathVariable("firstBoardId") Long firstBoardId, @PathVariable("firstBoardCommentId") Long firstBoardCommentId) {
        firstBoardCommentService.deleteFirstBoardComment(firstBoardId, firstBoardCommentId);
        return ResponseEntity.noContent().build();
    }

    
}
