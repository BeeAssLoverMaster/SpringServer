package shkond.server.controller.articles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shkond.server.model.articles.ArticleCategory;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.arts.ArtGenre;
import shkond.server.repository.article.ArticleCategoryRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @GetMapping("/art_categories/get_all")
    public ResponseEntity<?> getAllArticleCategories() {
        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findAll();

        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for (ArticleCategory category : articleCategoryList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", category.getId());
            jsonObject.addProperty("categoryName", category.getName());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("articleCategories", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }
}
