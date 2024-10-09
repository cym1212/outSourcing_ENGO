package OutSourcing.ENGO.domain.second_board.second_board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class SecondBoardListResponseDTO {
    private Long id;
    private String title;
    private String name;
    private int likeCount;
    private int viewCount;
}
