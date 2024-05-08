package shkond.server.repository.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.articles.Article;
import shkond.server.model.articles.ArticleCategory;
import shkond.server.model.articles.ArticleImage;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByArtGenreId(Long id);
}
