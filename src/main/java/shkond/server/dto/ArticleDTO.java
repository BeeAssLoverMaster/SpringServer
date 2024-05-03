package shkond.server.dto;

import java.util.List;

public class ArticleDTO {
    private Long categoryId;
    private Long genreId;
    private String context;
    private Long artistId;
    private List<String> texts; // Список текстов статьи, может быть несколько абзацев

    // Конструкторы, геттеры и сеттеры
    public ArticleDTO() {}

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }
}