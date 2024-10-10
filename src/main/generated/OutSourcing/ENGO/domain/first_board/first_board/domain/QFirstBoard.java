package OutSourcing.ENGO.domain.first_board.first_board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFirstBoard is a Querydsl query type for FirstBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFirstBoard extends EntityPathBase<FirstBoard> {

    private static final long serialVersionUID = 1680197821L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFirstBoard firstBoard = new QFirstBoard("firstBoard");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final OutSourcing.ENGO.domain.member.domain.QMember member;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QFirstBoard(String variable) {
        this(FirstBoard.class, forVariable(variable), INITS);
    }

    public QFirstBoard(Path<? extends FirstBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFirstBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFirstBoard(PathMetadata metadata, PathInits inits) {
        this(FirstBoard.class, metadata, inits);
    }

    public QFirstBoard(Class<? extends FirstBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

