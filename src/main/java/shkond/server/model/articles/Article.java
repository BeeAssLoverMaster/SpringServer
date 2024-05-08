package shkond.server.model.articles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import shkond.server.model.arts.ArtGenre;
import shkond.server.repository.article.ArticleCategoryRepository;
import shkond.server.repository.arts.ArtCategoryRepository;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class Article {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_category_id")
    private ArticleCategory articleCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private ArtGenre artGenre;

    public Article() {}

    public Article(
            String title,
            String text,
            ArticleCategory articleCategory,
            ArtGenre artGenre
    ) {
        this.title = title;
        this.text = text;
        this.articleCategory = articleCategory;
        this.artGenre = artGenre;
    }

}
