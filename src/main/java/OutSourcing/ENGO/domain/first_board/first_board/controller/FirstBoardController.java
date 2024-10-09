package OutSourcing.ENGO.domain.first_board.first_board.controller;

import OutSourcing.ENGO.domain.first_board.first_board.dto.request.FirstBoardCreatRequestDTO;
import OutSourcing.ENGO.domain.first_board.first_board.dto.response.FirstBoardDetailResponseDTO;
import OutSourcing.ENGO.domain.first_board.first_board.dto.response.FirstBoardListResponseDTO;
import OutSourcing.ENGO.domain.first_board.first_board.service.FirstBoardService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/first-board")
public class FirstBoardController {

    private final FirstBoardService firstBoardService;

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @PostMapping("/create")
    public ResponseEntity<String> createFirstBoard(@RequestBody FirstBoardCreatRequestDTO firstBoardCreatRequestDTO){
        try {
            firstBoardService.createFirstBoard(firstBoardCreatRequestDTO);
            return new ResponseEntity<>("게시글이 성공적으로 저장되었습니다", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("사용자가 인증되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글을 조회합니다. 한 번에 10개씩 조회됩니다.")
    @GetMapping
    public Page<FirstBoardListResponseDTO> getAllFirstBoards() {
        return firstBoardService.getAllFirstBoards(Pageable.ofSize(10));
    }

    @Operation(summary = "게시글 상세조회", description = "게시글을 상세조회합니다.")
    @GetMapping("{firstBoardId}")
    public FirstBoardDetailResponseDTO getFirstBoardDetail(@PathVariable("firstBoardId") Long firstBoardId) {
        return firstBoardService.getFirstBoardDetail(firstBoardId);
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PatchMapping("/{firstBoardId}")
    public ResponseEntity<String> updateFirstBoard (@PathVariable("firstBoardId") Long firstBoardId, @RequestBody FirstBoardCreatRequestDTO firstBoardCreatRequestDTO){
        try{
            firstBoardService.updateFirstBoard(firstBoardId, firstBoardCreatRequestDTO);
            return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "게시글 삭제", description = "본인이 작성한 게시물을 삭제합니다.")
    @DeleteMapping("{firstBoardId}")
    public void deleteFirstBoard(@PathVariable("firstBoardId") Long firstBoardId) {
        firstBoardService.deleteFirstBoard(firstBoardId);
    }
}
