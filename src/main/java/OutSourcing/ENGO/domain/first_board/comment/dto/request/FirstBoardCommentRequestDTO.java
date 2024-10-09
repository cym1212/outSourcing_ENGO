package OutSourcing.ENGO.domain.first_board.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FirstBoardCommentRequestDTO {
    private String comments;
}