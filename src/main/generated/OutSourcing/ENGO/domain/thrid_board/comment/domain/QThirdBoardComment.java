package OutSourcing.ENGO.domain.thrid_board.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThirdBoardComment is a Querydsl query type for ThirdBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThirdBoardComment extends EntityPathBase<ThirdBoardComment> {

    private static final long serialVersionUID = 1144395976L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThirdBoardComment thirdBoardComment = new QThirdBoardComment("thirdBoardComment");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    public final StringPath comments = createString("comments");

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

    public QThirdBoardComment(String variable) {
        this(ThirdBoardComment.class, forVariable(variable), INITS);
    }

    public QThirdBoardComment(Path<? extends ThirdBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThirdBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThirdBoardComment(PathMetadata metadata, PathInits inits) {
        this(ThirdBoardComment.class, metadata, inits);
    }

    public QThirdBoardComment(Class<? extends ThirdBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
        this.thirdBoard = inits.isInitialized("thirdBoard") ? new OutSourcing.ENGO.domain.thrid_board.third_board.domain.QThirdBoard(forProperty("thirdBoard"), inits.get("thirdBoard")) : null;
    }

}

