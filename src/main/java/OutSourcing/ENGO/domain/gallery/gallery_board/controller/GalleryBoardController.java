package OutSourcing.ENGO.domain.gallery.gallery_board.controller;

import OutSourcing.ENGO.domain.first_board.first_board.dto.response.FirstBoardListResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.domain.GalleryBoard;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.request.GalleryBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.dto.response.GalleryBoardListResponseDTO;
import OutSourcing.ENGO.domain.gallery.gallery_board.service.GalleryBoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery-board")
public class GalleryBoardController {

    private final GalleryBoardService galleryBoardService;

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createGalleryBoard(@ModelAttribute GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO){
        try {
            galleryBoardService.createGalleryBoard(galleryBoardCreatRequestDTO);
            return new ResponseEntity<>("게시글이 성공적으로 저장되었습니다", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("사용자가 인증되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "게시글 검색", description = "게시글을 검색합니다.")
    @GetMapping("/search")
    public Page<GalleryBoardListResponseDTO> searchGalleryBoard(
            @RequestParam("query") String query,
            @RequestParam("searchBy") String searchBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return galleryBoardService.searchBoards(query, searchBy, pageable);
    }

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글을 조회합니다. 페이징 처리를 지원합니다.")
    @GetMapping
    public Page<GalleryBoardListResponseDTO> getAllGalleryBoards(
            @RequestParam(value = "page", defaultValue = "0") int page, // 기본값 0으로 설정
            @RequestParam(value = "size", defaultValue = "10") int size // 기본값 10으로 설정
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return galleryBoardService.getAllGalleryBoards(pageable);
    }

    @Operation(summary = "게시글 상세조회", description = "게시글을 상세조회합니다.")
    @GetMapping("{galleryBoardId}")
    public GalleryBoardDetailResponseDTO getGalleryBoardDetail(@PathVariable("galleryBoardId") Long galleryBoardId) {
        return galleryBoardService.getGalleryBoardDetail(galleryBoardId);
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PatchMapping(value = "/{galleryBoardId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateGalleryBoard (@PathVariable("galleryBoardId") Long galleryBoardId, @ModelAttribute GalleryBoardCreatRequestDTO galleryBoardCreatRequestDTO){
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
