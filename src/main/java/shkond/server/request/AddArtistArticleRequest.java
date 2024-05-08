package shkond.server.request;

import lombok.Getter;

import java.util.List;

@Getter
public class AddArtistArticleRequest {
    private String title;
    private String biographyText;
    private final Long articleCategoryId = 4L;
    private final Long genreId = null;
    private List<String> description;
}
