package shkond.server.model.articles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import shkond.server.model.arts.ArtGenre;
import shkond.server.repository.article.ArticleCategoryRepository;
import shkond.server.repository.arts.ArtCategoryRepository;

/*Модель для статей*/
@Entity // Указывает на принадлежность класса к сущности JPA, позволяющая сохранять класс в БД
@Getter // Генерация геттеров
@Setter // Генерация сеттеров
@Table(name = "articles") // Указывает, что сущность будет соответствовать таблице в БД
public class Article {
    @Id // Первичный ключ
    @Column(name = "article_id") // Имя столбца в БД для поля "id"
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации первичного ключа
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;
    /* Отношение "многие-к-одному" между Article и ArticlleCategory */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_category_id")
    private ArticleCategory articleCategory;
    /* Отношение "многие-к-одному" между Article и ArtGenre */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private ArtGenre artGenre;
    public Article() {} // Пустой конструктор для JPA
    public Article(String title, String text, ArticleCategory articleCategory, ArtGenre artGenre) {
        this.title = title;
        this.text = text;
        this.articleCategory = articleCategory;
        this.artGenre = artGenre;
    }
}
