package OutSourcing.ENGO.domain.gallery.gallery_board.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class GalleryBoardCreatRequestDTO {
    private String title;
    private String content;
    private List<MultipartFile> images;
}
