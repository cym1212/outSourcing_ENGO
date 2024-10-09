package OutSourcing.ENGO.domain.second_board.like.repository;

import OutSourcing.ENGO.domain.first_board.like.domain.QLike;
import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.second_board.second_board.domain.SecondBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static OutSourcing.ENGO.domain.second_board.like.domain.QSecondBoardLike.secondBoardLike;

@Repository
@RequiredArgsConstructor
public class SecondBoardLikeRepositoryCustomImpl implements SecondBoardLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsBySecondBoardAndMemberQueryDSL(SecondBoard secondBoard, Member member) {

        return queryFactory
                .selectOne()
                .from(secondBoardLike)
                .where(secondBoardLike.secondBoard.eq(secondBoard)
                        .and(secondBoardLike.member.eq(member)))
                .fetchFirst() != null;
    }

    @Override
    public long countBySecondBoardQueryDSL(SecondBoard secondBoard) {

        Long count = queryFactory
                .select(secondBoardLike.count())
                .from(secondBoardLike)
                .where(secondBoardLike.secondBoard.eq(secondBoard))
                .fetchOne();

        return count != null ? count : 0L;
    }
}