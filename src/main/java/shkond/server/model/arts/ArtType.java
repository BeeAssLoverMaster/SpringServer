package shkond.server.model.arts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "art_types")
public class ArtType {
    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_name")
    private String name;
    @Column(name = "type_image")
    private String image;
    /* Отношение "многие-к-одному" между ArtType и ArtCategory */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_category_id")
    private ArtCategory artCategory;
}
