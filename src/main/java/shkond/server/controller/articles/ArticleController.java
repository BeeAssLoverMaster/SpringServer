package shkond.server.controller.articles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.Artist;
import shkond.server.model.articles.Article;
import shkond.server.model.articles.ArticleCategory;
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.arts.ArtGenre;
import shkond.server.repository.ArtistRepository;
import shkond.server.repository.article.ArticleCategoryRepository;
import shkond.server.repository.article.ArticleImageRepository;
import shkond.server.repository.article.ArticleRepository;
import shkond.server.repository.arts.ArtGenreRepository;
import shkond.server.request.articles.AddArticleRequest;
import shkond.server.request.articles.UpdateArticleRequest;
import shkond.server.security.service.ImageService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
/* Контроллер для статей */
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleImageRepository articleImageRepository;
    @Autowired
    private ArtGenreRepository artGenreRepository;
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ImageService imageService;
    @Value("${image.articles-dir}") // Ссылка на место хранения изображений
    private String assetDir;
    /**
     * Получение всех статей по жанру
     * @param genreId
     * @return JSON-объект, содержащий все статьи жанра
     */
    @GetMapping("/article/get_by_genre")
    public ResponseEntity<?> getAllArticlesByGenreId(@RequestParam(name = "genreId") Long genreId) {
        Optional<ArtGenre> artGenre = artGenreRepository.findById(genreId);
        List<Article> articleList = articleRepository.findAllByArtGenreId(artGenre.get().getId());
        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (Article article : articleList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", article.getId());
            jsonObject.addProperty("title", article.getTitle());
            jsonObject.addProperty("text", article.getText());
            jsonObject.addProperty("articleCategory", article.getArticleCategory().getId());
            jsonObject.addProperty("artGenre", article.getArtGenre().getId());
            jsonArray.add(jsonObject);
        }
        mainJsonObject.add("articles", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Получение всех статей по жанру и категории статьи
     * @param genreId
     * @return JSON-объект, содержащий все категории статьи определённой статьи
     */
    @GetMapping("/article/get_by_genre_and_article_category")
    public ResponseEntity<?> getAllArticlesByGenreIdAndArticleCategoryId(@RequestParam(name = "genreId") Long genreId) {
        Optional<ArtGenre> artGenre = artGenreRepository.findById(genreId);
        List<Article> articleList = articleRepository.findAllByArtGenreId(artGenre.get().getId());
        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (Article article : articleList) {
            if (article.getArticleCategory().getId() == 1) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", article.getId());
                jsonObject.addProperty("title", article.getTitle());
                jsonObject.addProperty("text", article.getText());
                jsonObject.addProperty("articleCategory", article.getArticleCategory().getId());
                jsonObject.addProperty("artGenre", article.getArtGenre().getId());
                jsonArray.add(jsonObject);
            }
        }
        mainJsonObject.add("articleImages", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }

    /**
     * Получение статьи представителя
     * @param artistId
     * @return JSON-объект, содержащий статью выбранного представителя
     */
    @GetMapping("article/get_by_artist_id")
    public ResponseEntity<?> getArticleByArtistId(@RequestParam(name = "artistId") Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", artist.get().getArticle().getId());
        jsonObject.addProperty("title", artist.get().getArticle().getTitle());
        jsonObject.addProperty("text", artist.get().getArticle().getText());
        jsonObject.addProperty("articleCategoryId", artist.get().getArticle().getArticleCategory().getId());
        String jsonString = jsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Обновление названия или текста в статье
     * @param updateArticleRequest
     * @return Сообщение "Статья успешно обновлена!"
     */
    @PostMapping("/article/update")
    public ResponseEntity<?> updateArticle(@RequestBody UpdateArticleRequest updateArticleRequest) {
        Optional<Article> existingArticleOpt = articleRepository.findById(updateArticleRequest.getId());
        if (!existingArticleOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Article article = existingArticleOpt.get();
        article.setTitle(updateArticleRequest.getTitle());
        article.setText(updateArticleRequest.getText());
        articleRepository.save(article);
        return ResponseEntity.ok("Статья успешно обновлена!");
    }
}
