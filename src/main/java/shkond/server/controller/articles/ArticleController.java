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

    @Value("${image.articles-dir}")
    private String assetDir;

    /* Web:
    * EditArticle
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

        mainJsonObject.add("articleImages", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

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

        mainJsonObject.add("articleImages", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }


    /* Никто не использует */
    @GetMapping("article/get_by_id")
    public ResponseEntity<?> getArticleById(@RequestParam(name = "articleId") Long articleId) {
        Optional<Article> articleOpt = articleRepository.findById(articleId);

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", articleOpt.get().getId());
        jsonObject.addProperty("title", articleOpt.get().getTitle());
        jsonObject.addProperty("text", articleOpt.get().getText());

        String jsonString = jsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

    /* Web:
    * EditRepresentative
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

    /* Никто не использует */
    @PostMapping("/article/add")
    public ResponseEntity<?> addArticle(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("request") AddArticleRequest request
    ) {
        Optional<ArtGenre> artGenreOptional = artGenreRepository.findById(request.getGenreId());
        Optional<ArticleCategory> articleCategoryOptional = articleCategoryRepository.findById(request.getArticleCategoryId());
        ArtGenre artGenre = null;
        ArticleCategory articleCategory = null;

        if (artGenreOptional.isPresent() && articleCategoryOptional.isPresent()) {
            artGenre = artGenreOptional.get();
            articleCategory = articleCategoryOptional.get();
        } else {
            return ResponseEntity.ok("artGenreOpt или articleCategoryOpt пустой");
        }

        Article addArticle = new Article(
                request.getTitle(),
                request.getText(),
                articleCategory,
                artGenre
        );

        Article article = articleRepository.save(addArticle);
        int count = 0;

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String imageName = imageService.saveImage(file, assetDir);

                ArticleImage image = new ArticleImage(imageName, article, request.getDescription().get(count));
                count++;
                articleImageRepository.save(image);
            }
        }

        return ResponseEntity.ok("Статья успешно сохранена");
    }

    /* Web:
    * EditArticle
    * EditRepresentative
    */
    @PostMapping("/article/update")
    public ResponseEntity<?> updateArticle(@RequestBody UpdateArticleRequest updateArticleRequest) {
        Optional<Article> existingArticleOpt = articleRepository.findById(updateArticleRequest.getId());
        if (!existingArticleOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Article article = existingArticleOpt.get();
        System.out.println("Article before update: " + article.getTitle() + ", " + article.getText());

        article.setTitle(updateArticleRequest.getTitle());
        article.setText(updateArticleRequest.getText());

        articleRepository.save(article);

        System.out.println("Article after update: " + article.getTitle() + ", " + article.getText());

        return ResponseEntity.ok("");
    }

}
