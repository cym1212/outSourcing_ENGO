package OutSourcing.ENGO.domain.first_board.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FirstBoardCommentRequestDTO {
    @NotNull
    private String comments;
}