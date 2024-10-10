package OutSourcing.ENGO.domain.second_board.second_board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSecondBoard is a Querydsl query type for SecondBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSecondBoard extends EntityPathBase<SecondBoard> {

    private static final long serialVersionUID = 1944289213L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSecondBoard secondBoard = new QSecondBoard("secondBoard");

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

    public QSecondBoard(String variable) {
        this(SecondBoard.class, forVariable(variable), INITS);
    }

    public QSecondBoard(Path<? extends SecondBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSecondBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSecondBoard(PathMetadata metadata, PathInits inits) {
        this(SecondBoard.class, metadata, inits);
    }

    public QSecondBoard(Class<? extends SecondBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

