package OutSourcing.ENGO.domain.gallery.gallery_board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGalleryImage is a Querydsl query type for GalleryImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGalleryImage extends EntityPathBase<GalleryImage> {

    private static final long serialVersionUID = 1734347949L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGalleryImage galleryImage = new QGalleryImage("galleryImage");

    public final OutSourcing.ENGO.global.domain.QBaseEntity _super = new OutSourcing.ENGO.global.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath fileName = createString("fileName");

    public final QGalleryBoard galleryBoard;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGalleryImage(String variable) {
        this(GalleryImage.class, forVariable(variable), INITS);
    }

    public QGalleryImage(Path<? extends GalleryImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGalleryImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGalleryImage(PathMetadata metadata, PathInits inits) {
        this(GalleryImage.class, metadata, inits);
    }

    public QGalleryImage(Class<? extends GalleryImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.galleryBoard = inits.isInitialized("galleryBoard") ? new QGalleryBoard(forProperty("galleryBoard"), inits.get("galleryBoard")) : null;
    }

}

