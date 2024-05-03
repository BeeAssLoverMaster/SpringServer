package shkond.server.model.arts;

import jakarta.persistence.*;

import java.util.List;

@Entity
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

