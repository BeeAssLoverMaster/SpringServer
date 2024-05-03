package shkond.server.model;

import jakarta.persistence.*;
import shkond.server.model.articles.Article;
import shkond.server.model.arts.ArtGenre;

import java.util.List;

@Entity
@Table(name = "artists")
public class Artist {
    public Artist(){};

    @Id
    @Column(name = "artist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artist_name", nullable = true)
    private String name;

    @Column(name = "artist_image", nullable = true)
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "artist_genres",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<ArtGenre> artGenres;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "artist_articles",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles;

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

    public List<ArtGenre> getArtGenres() {
        return artGenres;
    }

    public void setArtGenres(List<ArtGenre> artGenres) {
        this.artGenres = artGenres;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
