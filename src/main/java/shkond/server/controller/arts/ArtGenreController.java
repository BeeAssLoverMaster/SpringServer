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
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.arts.ArtType;
import shkond.server.repository.arts.ArtGenreRepository;
import shkond.server.repository.arts.ArtTypeRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArtGenreController {
    @Autowired
    ArtGenreRepository artGenreRepository;
    @GetMapping("/genres/get_all")
    public ResponseEntity<?> getAllGenres() {
        List<ArtGenre> genreList = artGenreRepository.findAll();

        JsonObject mainJsonObject = new JsonObject();

        String imageName = "http://10.0.2.2:8080/api/image/asset/";

        JsonArray jsonArray = new JsonArray();

        for (ArtGenre genre : genreList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", genre.getId());
            jsonObject.addProperty("genreName", genre.getName());
            jsonObject.addProperty("imageName", imageName + genre.getName() + ".png");
            jsonObject.addProperty("artType", genre.getArtType().getId());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("genres", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

    @GetMapping("/genres/get")
    public ResponseEntity<?> getGenresByTypeId(@RequestParam(name = "typeId") Long typeId) {
        List<ArtGenre> genreList = artGenreRepository.findAllByArtTypeId(typeId);

        JsonArray jsonArray = new JsonArray();
        JsonObject mainJsonObject = new JsonObject();

        String imageName = "http://10.0.2.2:8080/api/image/asset/";


        for (ArtGenre genre : genreList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", genre.getId());
            jsonObject.addProperty("genreName", genre.getName());
            jsonObject.addProperty("imageName", imageName + genre.getName() + ".png");
            jsonObject.addProperty("artTypeId", genre.getArtType().getId());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("genres", jsonArray); // Добавляем массив в основной JsonObject

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }
}
