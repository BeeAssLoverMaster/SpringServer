package shkond.server.model.articles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "article_images")
public class ArticleImage {
    @Id
    @Column(name = "article_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_name", nullable = true)
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
    @Column(name = "image_description")
    private String description;
    public ArticleImage() {}
    public ArticleImage(String image, Article article, String description) {
        this.image = image;
        this.article = article;
        this.description = description;
    }
}
