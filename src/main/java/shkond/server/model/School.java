package shkond.server.model;

import jakarta.persistence.*;
import shkond.server.model.arts.ArtCategory;

@Entity
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "school_id")
    private Long id;

    @Column(name = "school_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "art_category_id")
    private ArtCategory artCategory;


    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "school_image")
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

    public ArtCategory getArtCategory() {
        return artCategory;
    }

    public void setArtCategory(ArtCategory artCategory) {
        this.artCategory = artCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
