package shkond.server.model.articles;

import jakarta.persistence.*;
import shkond.server.model.arts.ArtGenre;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    public ArtGenre getArtGenre() {
        return artGenre;
    }

    public void setArtGenre(ArtGenre artGenre) {
        this.artGenre = artGenre;
    }
}
