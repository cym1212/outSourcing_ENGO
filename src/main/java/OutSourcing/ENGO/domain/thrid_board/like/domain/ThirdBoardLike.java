package OutSourcing.ENGO.domain.thrid_board.like.domain;

import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import OutSourcing.ENGO.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class ThirdBoardLike extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_board_id")
    private ThirdBoard thirdBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}