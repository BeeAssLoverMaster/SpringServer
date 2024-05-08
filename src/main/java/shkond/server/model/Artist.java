package shkond.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shkond.server.model.articles.Article;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.quizzes.Quiz;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "artists")
public class Artist {
    public Artist(){};

    public Artist(String name, String image, Article article) {
        this.name = name;
        this.image = image;
        this.article = article;
    }

    @Id
    @Column(name = "artist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artist_name", nullable = true)
    private String name;

    @Column(name = "artist_image", nullable = true)
    private String image;

    @OneToOne
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Article article;
}
