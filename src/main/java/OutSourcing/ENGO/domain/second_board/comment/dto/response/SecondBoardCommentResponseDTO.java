package OutSourcing.ENGO.domain.second_board.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SecondBoardCommentResponseDTO {
    private Long id;
    private String comments;
    private String memberName;
    private String boardTitle;
    private LocalDateTime modifiedAt;
    private boolean isDeleted;
}