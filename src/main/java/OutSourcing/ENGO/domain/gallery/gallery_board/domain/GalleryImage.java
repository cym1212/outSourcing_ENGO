package OutSourcing.ENGO.domain.gallery.gallery_board.domain;


import OutSourcing.ENGO.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gallery_image")
public class GalleryImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_board_id")
    private GalleryBoard galleryBoard;

    @Column(nullable = false)
    private String imageUrl;

    @Column
    private String fileName;
}
