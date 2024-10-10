package OutSourcing.ENGO.domain.first_board.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFirstBoardComment is a Querydsl query type for FirstBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFirstBoardComment extends EntityPathBase<FirstBoardComment> {

    private static final long serialVersionUID = -608753638L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFirstBoardComment firstBoardComment = new QFirstBoardComment("firstBoardComment");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    public final StringPath comments = createString("comments");

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

    public QFirstBoardComment(String variable) {
        this(FirstBoardComment.class, forVariable(variable), INITS);
    }

    public QFirstBoardComment(Path<? extends FirstBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFirstBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFirstBoardComment(PathMetadata metadata, PathInits inits) {
        this(FirstBoardComment.class, metadata, inits);
    }

    public QFirstBoardComment(Class<? extends FirstBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.firstBoard = inits.isInitialized("firstBoard") ? new OutSourcing.ENGO.domain.first_board.first_board.domain.QFirstBoard(forProperty("firstBoard"), inits.get("firstBoard")) : null;
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

