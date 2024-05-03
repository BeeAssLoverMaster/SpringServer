package shkond.server.controller.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.articles.Article;
import shkond.server.model.articles.ArticleCategory;
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.arts.ArtGenre;
import shkond.server.repository.article.ArticleCategoryRepository;
import shkond.server.repository.article.ArticleImageRepository;
import shkond.server.repository.article.ArticleRepository;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.arts.ArtGenreRepository;
import shkond.server.request.AddArticleRequest;
import shkond.server.security.service.ImageService;

import java.io.IOException;
import java.util.Arrays;
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
    private ImageService imageService;

    @Value("${image.articles-dir}")
    private String assetDir;

    @GetMapping("/article/get_by_genre")
    public ResponseEntity<?> getAllArticlesByGenreId(@RequestParam(name = "genreId") Long genreId) {
        Optional<ArtGenre> artGenre = artGenreRepository.findById(genreId);

        List<Article> articleList = articleRepository.findAllByArtGenreId(artGenre.get().getId());

        JsonObject mainJsonObject = new JsonObject();

        String imageUrl = "http://10.0.2.2:8080/api/image/article/";

        JsonArray jsonArray = new JsonArray();

        for (Article article : articleList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", article.getId());
            jsonObject.addProperty("text", article.getText());
            jsonObject.addProperty("articleCategory", article.getArticleCategory().getId());
            jsonObject.addProperty("artGenre", article.getArtGenre().getId());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("articleImages", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

    @PostMapping("/article/add")
    public ResponseEntity<?> addArticle(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("request") AddArticleRequest request
    ) {
        System.out.println("test");
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
}
