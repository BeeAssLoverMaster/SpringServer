package shkond.server.request.articles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/* Данные для обновления информации в статье */
public class UpdateArticleRequest {
    private Long id;
    private String title;
    private String text;
    private Long articleCategoryId;
}
