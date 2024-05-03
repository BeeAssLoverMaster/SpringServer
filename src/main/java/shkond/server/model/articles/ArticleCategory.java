package shkond.server.model.articles;

import jakarta.persistence.*;

@Entity
@Table(name = "article_categories")
public class ArticleCategory {

    @Id
    @Column(name = "article_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
