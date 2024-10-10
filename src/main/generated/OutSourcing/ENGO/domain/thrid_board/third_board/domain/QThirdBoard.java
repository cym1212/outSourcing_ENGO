package OutSourcing.ENGO.domain.thrid_board.third_board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThirdBoard is a Querydsl query type for ThirdBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThirdBoard extends EntityPathBase<ThirdBoard> {

    private static final long serialVersionUID = -1886781178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThirdBoard thirdBoard = new QThirdBoard("thirdBoard");

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

    public QThirdBoard(String variable) {
        this(ThirdBoard.class, forVariable(variable), INITS);
    }

    public QThirdBoard(Path<? extends ThirdBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThirdBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThirdBoard(PathMetadata metadata, PathInits inits) {
        this(ThirdBoard.class, metadata, inits);
    }

    public QThirdBoard(Class<? extends ThirdBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

