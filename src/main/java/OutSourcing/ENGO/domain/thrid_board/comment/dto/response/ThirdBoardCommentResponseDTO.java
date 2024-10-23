package OutSourcing.ENGO.domain.thrid_board.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThirdBoardCommentResponseDTO {
    private Long id;
    private Long authorId;
    private String comments;
    private String memberName;
    private String boardTitle;
    private LocalDateTime modifiedAt;
    private boolean isDeleted;
}