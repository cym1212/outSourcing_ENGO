package OutSourcing.ENGO.domain.thrid_board.like.repository;

import OutSourcing.ENGO.domain.first_board.like.domain.QLike;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.thrid_board.like.domain.QThirdBoardLike;
import OutSourcing.ENGO.domain.thrid_board.third_board.domain.ThirdBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static OutSourcing.ENGO.domain.thrid_board.like.domain.QThirdBoardLike.thirdBoardLike;

@Repository
@RequiredArgsConstructor
public class ThirdBoardLikeRepositoryCustomImpl implements ThirdBoardLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByThirdBoardAndMemberQueryDSL(ThirdBoard thirdBoard, Member member) {



        return queryFactory
                .selectOne()
                .from(thirdBoardLike)
                .where(thirdBoardLike.thirdBoard.eq(thirdBoard)
                        .and(thirdBoardLike.member.eq(member)))
                .fetchFirst() != null;
    }

    @Override
    public long countByThirdBoardQueryDSL(ThirdBoard thirdBoard) {

        Long count = queryFactory
                .select(thirdBoardLike.count())
                .from(thirdBoardLike)
                .where(thirdBoardLike.thirdBoard.eq(thirdBoard))
                .fetchOne();

        return count != null ? count : 0L;
    }
}