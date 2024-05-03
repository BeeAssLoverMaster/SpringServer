package shkond.server.repository.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.arts.ArtType;

import java.util.List;

@Repository
public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
    List<ArticleImage> findAllByArticleId(Long id);
}
