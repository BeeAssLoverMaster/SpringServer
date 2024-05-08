package shkond.server.model.quizzes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shkond.server.model.Artist;
import shkond.server.model.articles.Article;
import shkond.server.model.arts.ArtGenre;

@Entity
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public Quiz(){}

    public Quiz(ArtGenre artGenre, Article article) {
        this.artGenre = artGenre;
        this.article = article;
    }

}
