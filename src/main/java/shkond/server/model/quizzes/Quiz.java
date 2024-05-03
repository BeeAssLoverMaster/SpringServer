package shkond.server.model.quizzes;

import jakarta.persistence.*;
import shkond.server.model.articles.Article;
import shkond.server.model.arts.ArtGenre;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @Column(name = "quiz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_genre_id")
    private ArtGenre artGenre;

    @OneToOne
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Article article;

    public Quiz(){}

    public Quiz(ArtGenre artGenre, Article article) {
        this.artGenre = artGenre;
        this.article = article;
    }

    public Long getId() {
        return id;
    }

    public ArtGenre getArtGenre() {
        return artGenre;
    }

    public void setArtGenre(ArtGenre artGenre) {
        this.artGenre = artGenre;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
