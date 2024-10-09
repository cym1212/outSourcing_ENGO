package OutSourcing.ENGO.domain.gallery.gallery_board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class GalleryBoardCreatRequestDTO {
    private String title;
    private String content;
}
