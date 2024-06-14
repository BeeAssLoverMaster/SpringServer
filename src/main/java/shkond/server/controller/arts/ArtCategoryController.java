package shkond.server.controller.arts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.users.User;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.users.UserRepository;
import shkond.server.security.service.ImageService;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
/* Контроллер для категорий искусств */
public class ArtCategoryController {
    @Autowired
    ArtCategoryRepository artCategoryRepository;
    @Autowired
    UserRepository userRepository;

    /** Получение всех категорий искусств
     * @return JSON-объект, содержащий все категории исскуств
     */
    @GetMapping("/categories/get_all")
    public ResponseEntity<?> getAllCategories() {
        List<ArtCategory> categoryList = artCategoryRepository.findAll();
        JsonObject mainJsonObject = new JsonObject();
        String imageName = "http://192.168.1.6:8080/api/image/asset/";
        JsonArray jsonArray = new JsonArray();
        for (ArtCategory category : categoryList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", category.getId());
            jsonObject.addProperty("categoryName", category.getName());
            jsonObject.addProperty("imageName", imageName + category.getName() + ".png");
            jsonArray.add(jsonObject);
        }
        mainJsonObject.add("categories", jsonArray); // Добавляем массив в основной JsonObject
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }

    /**
     * Получение выбранной пользователем категории искусства
     * @param username
     * @return JSON-объект, содержащий идентификатор, название и изображение категории
     */
    @GetMapping("/categories/get")
    public ResponseEntity<?> getCategoryByUserId(@RequestParam(name = "username") String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<ArtCategory> artCategory = artCategoryRepository.findById(user.get().getArtCategory().getId());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", artCategory.get().getId());
        jsonObject.addProperty("categoryName", artCategory.get().getName());
        jsonObject.addProperty("imageName", "http://192.168.1.6:8080/api/image/asset/" + artCategory.get().getName() + ".png");
        String jsonString = jsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
}
