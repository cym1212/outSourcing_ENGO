package OutSourcing.ENGO.domain.thrid_board.like.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThirdBoardLike is a Querydsl query type for ThirdBoardLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThirdBoardLike extends EntityPathBase<ThirdBoardLike> {

    private static final long serialVersionUID = 855184108L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThirdBoardLike thirdBoardLike = new QThirdBoardLike("thirdBoardLike");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final OutSourcing.ENGO.domain.member.domain.QMember member;

    public final OutSourcing.ENGO.domain.thrid_board.third_board.domain.QThirdBoard thirdBoard;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QThirdBoardLike(String variable) {
        this(ThirdBoardLike.class, forVariable(variable), INITS);
    }

    public QThirdBoardLike(Path<? extends ThirdBoardLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThirdBoardLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThirdBoardLike(PathMetadata metadata, PathInits inits) {
        this(ThirdBoardLike.class, metadata, inits);
    }

    public QThirdBoardLike(Class<? extends ThirdBoardLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
        this.thirdBoard = inits.isInitialized("thirdBoard") ? new OutSourcing.ENGO.domain.thrid_board.third_board.domain.QThirdBoard(forProperty("thirdBoard"), inits.get("thirdBoard")) : null;
    }

}

