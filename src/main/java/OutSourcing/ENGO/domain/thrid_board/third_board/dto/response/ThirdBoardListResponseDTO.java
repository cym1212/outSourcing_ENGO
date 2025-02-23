package OutSourcing.ENGO.domain.thrid_board.third_board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ThirdBoardListResponseDTO {
    private Long id;
    private String title;
    private String name;
    private LocalDateTime createdAt;
    private int likeCount;
    private int viewCount;
}
