package OutSourcing.ENGO.domain.gallery.gallery_board.controller;

import OutSourcing.ENGO.domain.gallery.gallery_board.dto.request.GalleryBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardListResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.service.GalleryBoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery-board")
public class GalleryBoardController {

    private final GalleryBoardService galleryBoardService;

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @PostMapping("/create")
    public ResponseEntity<String> createGalleryBoard(@RequestBody GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO){
        try {
            galleryBoardService.createGalleryBoard(galleryBoardCreatRequestDTO);
            return new ResponseEntity<>("게시글이 성공적으로 저장되었습니다", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("사용자가 인증되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글을 조회합니다. 한 번에 10개씩 조회됩니다.")
    @GetMapping
    public Page<GalleryBoardListResponseDTO> getAllGalleryBoards() {
        return galleryBoardService.getAllGalleryBoards(Pageable.ofSize(10));
    }

    @Operation(summary = "게시글 상세조회", description = "게시글을 상세조회합니다.")
    @GetMapping("{galleryBoardId}")
    public GalleryBoardDetailResponseDTO getGalleryBoardDetail(@PathVariable("galleryBoardId") Long galleryBoardId) {
        return galleryBoardService.getGalleryBoardDetail(galleryBoardId);
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PatchMapping("/{galleryBoardId}")
    public ResponseEntity<String> updateGalleryBoard (@PathVariable("galleryBoardId") Long galleryBoardId, @RequestBody GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO){
        try{
            galleryBoardService.updateGalleryBoard(galleryBoardId, galleryBoardCreatRequestDTO);
            return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "게시글 삭제", description = "본인이 작성한 게시물을 삭제합니다.")
    @DeleteMapping("{galleryBoardId}")
    public void deleteGalleryBoard(@PathVariable("galleryBoardId") Long galleryBoardId) {
        galleryBoardService.deleteGalleryBoard(galleryBoardId);
    }
}
