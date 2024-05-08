package shkond.server.model.arts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "art_categories")
public class ArtCategory {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = true)
    private String  name;

    @Column(name = "category_image", nullable = true)
    private String image;
}

