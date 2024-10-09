package OutSourcing.ENGO.domain.gallery.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryBoardCommentRequestDTO {
    private String comments;
}