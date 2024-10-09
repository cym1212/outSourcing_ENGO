package OutSourcing.ENGO.domain.thrid_board.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThirdBoardCommentRequestDTO {
    private String comments;
}