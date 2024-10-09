package OutSourcing.ENGO.domain.second_board.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SecondBoardCommentRequestDTO {
    private String comments;
}