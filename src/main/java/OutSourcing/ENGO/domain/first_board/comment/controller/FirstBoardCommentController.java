package OutSourcing.ENGO.domain.first_board.comment.controller;


import OutSourcing.ENGO.domain.first_board.comment.dto.request.FirstBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.first_board.comment.dto.response.FirstBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.first_board.comment.service.FirstBoardCommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/first-board/{firstBoardId}/first-board-comments")
public class FirstBoardCommentController {

    @Autowired
    private FirstBoardCommentService firstBoardCommentService;

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

    //todo 1. 게시글이랑 댓글 로직 테스트 하기 (O) / 2. 좋아요 로직 구현하기 (O) / 3. 게시판 4개로 복사하기(O) / 4. 게시판 하나 이미지 업로드 할 수 있게 바꾸기 / 5. 전체적인 테스트 및 디테일 구현 (비밀번호 재설정 로직 같은거) /6. 관리자 계정 생성해서 테스트
}
