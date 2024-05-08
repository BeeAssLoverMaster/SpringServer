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
import shkond.server.model.arts.ArtType;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.arts.ArtTypeRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArtTypeController {
    @Autowired
    ArtTypeRepository artTypeRepository;

    @Autowired
    ArtCategoryRepository artCategoryRepository;

    private final String IMAGE_URL = "http://10.0.2.2:8080/api/image/asset/";

    /* Никем не используется */
    @GetMapping("/types/get_all")
    public ResponseEntity<?> getAllTypes() {
        List<ArtType> typeList = artTypeRepository.findAll();

        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for (ArtType type : typeList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", type.getId());
            jsonObject.addProperty("typeName", type.getName());
            jsonObject.addProperty("imageName", IMAGE_URL + type.getName() + ".png");
            jsonObject.addProperty("artCategory", type.getArtCategory().getId());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("types", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

    /* Web:
    * ArticleAndQuiz
    */
    @GetMapping("/types/get")
    public ResponseEntity<?> getTypesByCategoryId(@RequestParam(name = "categoryId") Long categoryId) {
        List<ArtType> typeList = artTypeRepository.findAllByArtCategoryId(categoryId);


        JsonArray jsonArray = new JsonArray();
        JsonObject mainJsonObject = new JsonObject();
        for (ArtType type : typeList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", type.getId());
            jsonObject.addProperty("typeName", type.getName());
            jsonObject.addProperty("imageName", IMAGE_URL + type.getName() + ".png");
            jsonObject.addProperty("artCategory", type.getArtCategory().getId());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("types", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);

    }
}
