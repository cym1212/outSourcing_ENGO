package OutSourcing.ENGO.domain.thrid_board.third_board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ThirdBoardDetailResponseDTO {
    private Long id;


    private String title;

    private String name;
    private String content;
    private int likeCount;
    private int viewCount;

    //TODO 댓글 추가하세요
}
