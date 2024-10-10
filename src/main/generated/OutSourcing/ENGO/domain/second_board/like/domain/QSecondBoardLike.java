package OutSourcing.ENGO.domain.second_board.like.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSecondBoardLike is a Querydsl query type for SecondBoardLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSecondBoardLike extends EntityPathBase<SecondBoardLike> {

    private static final long serialVersionUID = 816356152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSecondBoardLike secondBoardLike = new QSecondBoardLike("secondBoardLike");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final OutSourcing.ENGO.domain.member.domain.QMember member;

    public final OutSourcing.ENGO.domain.second_board.second_board.domain.QSecondBoard secondBoard;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSecondBoardLike(String variable) {
        this(SecondBoardLike.class, forVariable(variable), INITS);
    }

    public QSecondBoardLike(Path<? extends SecondBoardLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSecondBoardLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSecondBoardLike(PathMetadata metadata, PathInits inits) {
        this(SecondBoardLike.class, metadata, inits);
    }

    public QSecondBoardLike(Class<? extends SecondBoardLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
        this.secondBoard = inits.isInitialized("secondBoard") ? new OutSourcing.ENGO.domain.second_board.second_board.domain.QSecondBoard(forProperty("secondBoard"), inits.get("secondBoard")) : null;
    }

}

