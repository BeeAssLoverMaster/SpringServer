package shkond.server.controller.articles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shkond.server.model.articles.Article;
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.arts.ArtGenre;
import shkond.server.repository.article.ArticleImageRepository;
import shkond.server.repository.article.ArticleRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleImageController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleImageRepository articleImageRepository;

    /* Никем не используется */
    @GetMapping("/article_images/get")
    public ResponseEntity<?> getAllArticleImagesByArticleId(@RequestParam(name = "articleId") Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        Optional<ArticleImage> articleImage = articleImageRepository.findById(article.get().getId());
        List<ArticleImage> articleImageList = articleImageRepository.findAllByArticleId(articleImage.get().getId());
        JsonObject mainJsonObject = new JsonObject();

//        String imageUrl = "http://10.0.2.2:8080/api/image/article/";
        String imageUrl = "http://192.168.1.6:8080/api/image/article/";

        JsonArray jsonArray = new JsonArray();

        for (ArticleImage image : articleImageList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", image.getId());
            jsonObject.addProperty("articleImage", imageUrl + image.getImage() + ".png");
            jsonObject.addProperty("articleId", image.getArticle().getId());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("articleImages", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }
}
