package OutSourcing.ENGO.domain.gallery.gallery_board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGalleryBoard is a Querydsl query type for GalleryBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGalleryBoard extends EntityPathBase<GalleryBoard> {

    private static final long serialVersionUID = 1727943224L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGalleryBoard galleryBoard = new QGalleryBoard("galleryBoard");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<GalleryImage, QGalleryImage> images = this.<GalleryImage, QGalleryImage>createList("images", GalleryImage.class, QGalleryImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final OutSourcing.ENGO.domain.member.domain.QMember member;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QGalleryBoard(String variable) {
        this(GalleryBoard.class, forVariable(variable), INITS);
    }

    public QGalleryBoard(Path<? extends GalleryBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGalleryBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGalleryBoard(PathMetadata metadata, PathInits inits) {
        this(GalleryBoard.class, metadata, inits);
    }

    public QGalleryBoard(Class<? extends GalleryBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new OutSourcing.ENGO.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

