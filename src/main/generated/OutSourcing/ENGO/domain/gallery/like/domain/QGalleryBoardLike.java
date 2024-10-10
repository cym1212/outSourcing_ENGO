package OutSourcing.ENGO.domain.gallery.like.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGalleryBoardLike is a Querydsl query type for GalleryBoardLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGalleryBoardLike extends EntityPathBase<GalleryBoardLike> {

    private static final long serialVersionUID = 140181903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGalleryBoardLike galleryBoardLike = new QGalleryBoardLike("galleryBoardLike");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

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

    public QGalleryBoardLike(String variable) {
        this(GalleryBoardLike.class, forVariable(variable), INITS);
    }

    public QGalleryBoardLike(Path<? extends GalleryBoardLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGalleryBoardLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGalleryBoardLike(PathMetadata metadata, PathInits inits) {
        this(GalleryBoardLike.class, metadata, inits);
    }

    public QGalleryBoardLike(Class<? extends GalleryBoardLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.galleryBoard = inits.isInitialized("galleryBoard") ? new OutSourcing.ENGO.domain.gallery.gallery_board.domain.QGalleryBoard(forProperty("galleryBoard"), inits.get("galleryBoard")) : null;
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

