package OutSourcing.ENGO.domain.gallery.comment.controller;


import OutSourcing.ENGO.domain.gallery.comment.dto.request.GalleryBoardCommentRequestDTO;
import OutSourcing.ENGO.domain.gallery.comment.dto.response.GalleryBoardCommentResponseDTO;
import OutSourcing.ENGO.domain.gallery.comment.service.GalleryBoardCommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gallery-board/{galleryBoardId}/gallery-board-comments")
public class GalleryBoardCommentController {

    @Autowired
    private GalleryBoardCommentService galleryBoardCommentService;

    @GetMapping
    @Operation(summary = "게시판 댓글 조회", description = "해당 게시판에 속해있는 댓글들을 페이징하여 조회합니다.")
    public ResponseEntity<Page<GalleryBoardCommentResponseDTO>> getGalleryBoardComments(@PathVariable("galleryBoardId") Long galleryBoardId) {
        Page<GalleryBoardCommentResponseDTO> comments = galleryBoardCommentService.getGalleryBoardComments(galleryBoardId, Pageable.ofSize(10));
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/create")
    @Operation(summary = "게시판 댓글 저장", description = "새로운 댓글을 저장합니다.")
    public ResponseEntity<GalleryBoardCommentResponseDTO> createGalleryBoardComment(@PathVariable("galleryBoardId") Long galleryBoardId, @RequestBody GalleryBoardCommentRequestDTO galleryBoardCommentRequestDTO) {
        GalleryBoardCommentResponseDTO responseDto = galleryBoardCommentService.createGalleryBoardComment(galleryBoardId, galleryBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/update/{galleryBoardCommentId}")
    @Operation(summary = "게시판 댓글 수정", description = "작성한 댓글을 수정합니다. (작성자만 가능)")
    public ResponseEntity<GalleryBoardCommentResponseDTO> updateGalleryBoardComment(@PathVariable("galleryBoardId") Long galleryBoardId, @PathVariable("galleryBoardCommentId") Long galleryBoardCommentId, @RequestBody GalleryBoardCommentRequestDTO galleryBoardCommentRequestDTO) {
        GalleryBoardCommentResponseDTO responseDto = galleryBoardCommentService.updateGalleryBoardComment(galleryBoardId, galleryBoardCommentId, galleryBoardCommentRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/delete/{galleryBoardCommentId}")
    @Operation(summary = "게시판 댓글 삭제", description = "작성한 댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteGalleryBoardComment(@PathVariable("galleryBoardId") Long galleryBoardId, @PathVariable("galleryBoardCommentId") Long galleryBoardCommentId) {
        galleryBoardCommentService.deleteGalleryBoardComment(galleryBoardId, galleryBoardCommentId);
        return ResponseEntity.noContent().build();
    }

    //todo 1. 게시글이랑 댓글 로직 테스트 하기 (O) / 2. 좋아요 로직 구현하기 (O) / 3. 게시판 4개로 복사하기 / 4. 게시판 하나 이미지 업로드 할 수 있게 바꾸기 / 5. 전체적인 테스트 및 디테일 구현 (비밀번호 재설정 로직 같은거) /6. 관리자 계정 생성해서 테스트
}
