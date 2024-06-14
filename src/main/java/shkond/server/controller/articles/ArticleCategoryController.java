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


@Controller // Устанавливает класс, как контроллер Spring
@RequiredArgsConstructor
@RequestMapping("/api") // Задаёт базовый URL для всех методов в контроллере
/* Контроллер для категорий статьи */
public class ArticleCategoryController {
    @Autowired // Внедряет зависимости
    private ArticleCategoryRepository articleCategoryRepository;

    /**
     * Получение всех категорий статей
     * @return JSON-объект, содержащий все статьи
     */
    @GetMapping("/article_categories/get_all") // Обрабатывает GET-запросы по указанной URL
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
