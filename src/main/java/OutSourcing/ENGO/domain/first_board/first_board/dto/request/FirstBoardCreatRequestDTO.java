package OutSourcing.ENGO.domain.first_board.first_board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class FirstBoardCreatRequestDTO {
    private String title;
    private String content;
}
