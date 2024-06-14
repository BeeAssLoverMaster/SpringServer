package shkond.server.request.articles;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.articles.ArticleCategory;
import shkond.server.model.arts.ArtGenre;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/* Данные для добавления статьи в БД */
public class AddArticleRequest {
    private String title;
    private String text;
    private Long articleCategoryId;
    private Long genreId;
    private List<String> description;
}
