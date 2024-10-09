package OutSourcing.ENGO.domain.gallery.gallery_board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class GalleryBoardListResponseDTO {
    private Long id;
    private String title;
    private String name;
    private int likeCount;
    private int viewCount;
}
