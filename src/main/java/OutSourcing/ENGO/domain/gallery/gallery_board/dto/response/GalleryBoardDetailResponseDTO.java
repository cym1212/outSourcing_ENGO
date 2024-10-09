package OutSourcing.ENGO.domain.gallery.gallery_board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class GalleryBoardDetailResponseDTO {
    private Long id;


    private String title;

    private String name;
    private String content;
    private int likeCount;
    private int viewCount;

    //TODO 댓글 추가하세요
}
