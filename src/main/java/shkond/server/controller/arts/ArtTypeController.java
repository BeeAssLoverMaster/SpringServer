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
/* Контроллер для типов искусства */
public class ArtTypeController {
    @Autowired
    ArtTypeRepository artTypeRepository;
    @Autowired
    ArtCategoryRepository artCategoryRepository;
    private final String IMAGE_URL = "http://192.168.1.6:8080/api/image/asset/";

    /**
     * Получение типов искусства по идентификатору категории искусства
     * @param categoryId
     * @return JSON-объект, содержащий идентификатор, название, изображение типа искусства и идентификатор категории искусства
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
            jsonObject.addProperty("imageName", IMAGE_URL + type.getImage() + ".png");
            jsonObject.addProperty("artCategory", type.getArtCategory().getId());
            jsonArray.add(jsonObject);
        }
        mainJsonObject.add("types", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
}

