package shkond.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.Artist;
import shkond.server.model.articles.Article;
import shkond.server.model.quizzes.Quiz;
import shkond.server.repository.ArtistRepository;
import shkond.server.repository.article.ArticleRepository;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.arts.ArtGenreRepository;
import shkond.server.repository.quizzes.QuizRepository;
import shkond.server.request.AddArtistArticleRequest;
import shkond.server.request.AddArtistRequest;
import shkond.server.request.articles.AddArticleRequest;
import shkond.server.security.service.ArtistService;
import shkond.server.security.service.ImageService;
import shkond.server.security.service.QuizService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtGenreRepository artGenreRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArtCategoryRepository artCategoryRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;
    @Autowired
    ImageService imageService;
    @Autowired
    private ArtistService artistService;
    @Value("${image.artists-dir}")
    private String artistsDir;
    @Value("${image.articles-dir}")
    private String articleDir;

    /* Web:
    * EditRepresentative*/
    @GetMapping("/artist/get_all")
    public ResponseEntity<?> getAllArtist() {
        List<Artist> artistList = artistService.getAllArtists();

        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for (Artist artist : artistList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", artist.getId());
            jsonObject.addProperty("artistName", artist.getName());
            jsonObject.addProperty("artistImage", artist.getImage());
            jsonObject.addProperty("articleId", artist.getArticle().getId());

            List<Quiz> quizList = quizRepository.findAllByArtistId(artist.getId());
            JsonArray jsonArrayQuizzes = new JsonArray();

            for (Quiz quiz : quizList) {
                JsonObject jsonObjectForQuiz = new JsonObject();

                jsonObjectForQuiz.addProperty("quizId", quiz.getId());

                jsonArrayQuizzes.add(jsonObjectForQuiz);
            }
            jsonObject.add("quizzes", jsonArrayQuizzes);

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("artists", jsonArray);

        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }

    /* Web:
    * EditRepresentative
    */
    @GetMapping("artist/get_by_id")
    public ResponseEntity<?> getArtistById(@RequestParam(name = "artistId") Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);

        //URL для мобильного устройства
        String imageUrlMobile = "http://10.0.2.2:8080/api/image/artist/";

        //URL для сайта
        String imageUrlWeb = "http://localhost:8080/api/image/artist/";

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", artist.get().getId());
        jsonObject.addProperty("artistName", artist.get().getName());
        jsonObject.addProperty("artistImage", imageUrlWeb + artist.get().getImage());

        String jsonString = jsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

    /* Web:
    * AddRepresentative
    */
    @PostMapping(value = "/artist/add")
    public ResponseEntity<?> addArtist(@RequestPart("profileImageFile") MultipartFile profileImageFile,
                                       @RequestPart(value = "bioImageFiles", required = false) MultipartFile[] bioImageFiles,
                                       @RequestPart("articleRequest") AddArticleRequest addArticleRequest,
                                       @RequestPart("artistRequest") AddArtistRequest addArtistRequest) {


        String imageName = imageService.saveImage(profileImageFile, artistsDir);

        addArticleRequest.setArticleCategoryId(4L);

        Article article = quizService.addArticle(bioImageFiles, addArticleRequest);

        Artist artist = new Artist(
                addArtistRequest.getArtistName(),
                imageName,
                article
        );

        artistRepository.save(artist);

        return ResponseEntity.ok("Представитель добавлен!");
    }

    /* Web:
    * EditRepresentative
    */
    @PostMapping(value = "/artist/update")
    public ResponseEntity<?> updateArtist(@RequestPart(name = "request") AddArtistRequest request,
                                          @RequestPart("file") MultipartFile file) {
        Optional<Artist> existingArtistOpt = artistRepository.findById(request.getArtistId());

        String imageName = imageService.saveImage(file, artistsDir);

        existingArtistOpt.get().setName(request.getArtistName());
        existingArtistOpt.get().setImage(imageName);

        artistRepository.save(existingArtistOpt.get());

        return ResponseEntity.ok("Представитель успешно сохранён");
    }
}
