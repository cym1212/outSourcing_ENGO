package OutSourcing.ENGO.domain.first_board.like.repository;

import OutSourcing.ENGO.domain.first_board.first_board.domain.FirstBoard;
import OutSourcing.ENGO.domain.first_board.like.domain.QLike;
import OutSourcing.ENGO.domain.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByFirstBoardAndMemberQueryDSL(FirstBoard firstBoard, Member member) {
        QLike like = QLike.like;

        return queryFactory
                .selectOne()
                .from(like)
                .where(like.firstBoard.eq(firstBoard)
                        .and(like.member.eq(member)))
                .fetchFirst() != null;
    }

    @Override
    public long countByFirstBoardQueryDSL(FirstBoard firstBoard) {
        QLike like = QLike.like;

        Long count = queryFactory
                .select(like.count())
                .from(like)
                .where(like.firstBoard.eq(firstBoard))
                .fetchOne();

        return count != null ? count : 0L;
    }
}