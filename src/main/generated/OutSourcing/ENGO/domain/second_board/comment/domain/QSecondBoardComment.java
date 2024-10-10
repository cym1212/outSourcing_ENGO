package OutSourcing.ENGO.domain.second_board.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSecondBoardComment is a Querydsl query type for SecondBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSecondBoardComment extends EntityPathBase<SecondBoardComment> {

    private static final long serialVersionUID = 641987370L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSecondBoardComment secondBoardComment = new QSecondBoardComment("secondBoardComment");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    public final StringPath comments = createString("comments");

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

    public QSecondBoardComment(String variable) {
        this(SecondBoardComment.class, forVariable(variable), INITS);
    }

    public QSecondBoardComment(Path<? extends SecondBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSecondBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSecondBoardComment(PathMetadata metadata, PathInits inits) {
        this(SecondBoardComment.class, metadata, inits);
    }

    public QSecondBoardComment(Class<? extends SecondBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
        this.secondBoard = inits.isInitialized("secondBoard") ? new OutSourcing.ENGO.domain.second_board.second_board.domain.QSecondBoard(forProperty("secondBoard"), inits.get("secondBoard")) : null;
    }

}

