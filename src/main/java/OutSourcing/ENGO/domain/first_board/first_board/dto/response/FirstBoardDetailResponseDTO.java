package OutSourcing.ENGO.domain.first_board.first_board.dto.response;

import OutSourcing.ENGO.global.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class FirstBoardDetailResponseDTO {
    private Long id;
    private Long authorId;
    private String title;

    private String name;
    private LocalDateTime createdAt;
    private String content;
    private int likeCount;
    private int viewCount;

    private Long requestUserId;
    private Role requestUserRole;

}
