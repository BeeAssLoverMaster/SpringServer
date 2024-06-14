package shkond.server.model.arts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "art_genres")
public class ArtGenre {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "genre_name", nullable = true)
    private String name;
    @Column(name = "genre_image", nullable = true)
    private String image;
    /* Отношение "многие-к-одному" между ArtGenre и ArtType */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_type_id")
    private ArtType artType;
}
