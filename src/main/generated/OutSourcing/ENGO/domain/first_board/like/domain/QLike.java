package OutSourcing.ENGO.domain.first_board.like.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLike is a Querydsl query type for Like
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLike extends EntityPathBase<Like> {

    private static final long serialVersionUID = -314570346L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLike like = new QLike("like1");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard firstBoard;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final OutSourcing.ENGO.domain.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QLike(String variable) {
        this(Like.class, forVariable(variable), INITS);
    }

    public QLike(Path<? extends Like> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLike(PathMetadata metadata, PathInits inits) {
        this(Like.class, metadata, inits);
    }

    public QLike(Class<? extends Like> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.firstBoard = inits.isInitialized("firstBoard") ? new OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard(forProperty("firstBoard"), inits.get("firstBoard")) : null;
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

