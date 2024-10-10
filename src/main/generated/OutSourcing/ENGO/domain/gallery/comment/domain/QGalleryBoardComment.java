package OutSourcing.ENGO.domain.gallery.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGalleryBoardComment is a Querydsl query type for GalleryBoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGalleryBoardComment extends EntityPathBase<GalleryBoardComment> {

    private static final long serialVersionUID = -13653151L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGalleryBoardComment galleryBoardComment = new QGalleryBoardComment("galleryBoardComment");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    public final StringPath comments = createString("comments");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final OutSourcing.ENGO.domain.gallery.gallery_board.domain.QGalleryBoard galleryBoard;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final OutSourcing.ENGO.domain.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGalleryBoardComment(String variable) {
        this(GalleryBoardComment.class, forVariable(variable), INITS);
    }

    public QGalleryBoardComment(Path<? extends GalleryBoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGalleryBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGalleryBoardComment(PathMetadata metadata, PathInits inits) {
        this(GalleryBoardComment.class, metadata, inits);
    }

    public QGalleryBoardComment(Class<? extends GalleryBoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.galleryBoard = inits.isInitialized("galleryBoard") ? new OutSourcing.ENGO.domain.gallery.gallery_board.domain.QGalleryBoard(forProperty("galleryBoard"), inits.get("galleryBoard")) : null;
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

