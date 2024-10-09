package OutSourcing.ENGO.domain.second_board.second_board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class SecondBoardCreatRequestDTO {
    private String title;
    private String content;
}
