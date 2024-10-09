package OutSourcing.ENGO.domain.thrid_board.third_board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ThirdBoardCreatRequestDTO {
    private String title;
    private String content;
}
