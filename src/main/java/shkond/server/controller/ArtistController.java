package shkond.server.controller;

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
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.quizzes.Quiz;
import shkond.server.repository.ArtistRepository;
import shkond.server.repository.article.ArticleImageRepository;
import shkond.server.repository.article.ArticleRepository;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.arts.ArtGenreRepository;
import shkond.server.repository.quizzes.QuizRepository;
import shkond.server.request.AddArtistRequest;
import shkond.server.request.articles.AddArticleRequest;
import shkond.server.security.service.ArtistService;
import shkond.server.security.service.ImageService;
import shkond.server.security.service.QuizService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
/* Контроллер для художников */
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
    private ArticleImageRepository articleImageRepository;
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
    private final String ARTICLE_IMAGE_URL = "http://192.168.1.6:8080/api/image/article/";
    private final String ARTIST_IMAGE_URL = "http://192.168.1.6:8080/api/image/artist/";
    /**
     * Получение всех представителей
     * @return JSON-объект со списком представителей
     */
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
    /**
     * Получение представителя по статье
     * @param articleId
     * @return JSON-объект с данными представителя
     */
    @GetMapping("/artist/get_artist_by_article")
    public ResponseEntity<?> getArtistByArticleId(@RequestParam(name = "articleId") Long articleId) {
        Optional<Artist> artistOptional = artistRepository.findByArticleId(articleId);
        String imageUrl = "http://192.168.1.6:8080/api/image/artist/";
        if (!artistOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Художник по данной статье не найден.");
        }
        Artist artist = artistOptional.get();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", artist.getId());
        jsonObject.addProperty("artistName", artist.getName());
        jsonObject.addProperty("imageUrl", imageUrl + artist.getImage() + ".png");
        String jsonString = jsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Получение статьи по идентификатору представителя
     * @param artistId
     * @return JSON-объект с данными статьи
     */
    @GetMapping("artist/get_by_id/article")
    public ResponseEntity<?> getArticleByArtistId(@RequestParam(name = "artistId") Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        Optional<Article> article = articleRepository.findById(artist.get().getArticle().getId());
        if (!article.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<ArticleImage> articleImageList = articleImageRepository.findAllByArticleId(article.get().getId());
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", artist.get().getId());
        jsonObject.addProperty("artistName", artist.get().getName());
        jsonObject.addProperty("artistImage", ARTIST_IMAGE_URL + artist.get().getImage());
        jsonObject.addProperty("articleId", artist.get().getArticle().getId());
        jsonObject.addProperty("title", artist.get().getArticle().getTitle());
        jsonObject.addProperty("text", artist.get().getArticle().getText());
        for (ArticleImage articleImage : articleImageList) {
            JsonObject jsonObjectImage = new JsonObject();
            jsonObjectImage.addProperty("imageName", ARTICLE_IMAGE_URL + articleImage.getImage());
            jsonObjectImage.addProperty("imageDescription", articleImage.getDescription());
            jsonArray.add(jsonObjectImage);
        }
        jsonObject.add("articleImages", jsonArray);
        String jsonString = jsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Получение предсавителя по идентификатору
     * @param artistId
     * @return JSON-объект с данными художника
     */
    @GetMapping("artist/get_by_id")
    public ResponseEntity<?> getArtistById(@RequestParam(name = "artistId") Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        String imageUrlMobile = "http://192.168.1.6:8080/api/image/artist/";
        String imageUrlWeb = "http://localhost:8080/api/image/artist/";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", artist.get().getId());
        jsonObject.addProperty("artistName", artist.get().getName());
        jsonObject.addProperty("artistImage", imageUrlWeb + artist.get().getImage());
        String jsonString = jsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Получение представителя по жанру
     * @param genreId
     * @return JSON-объект с данными представителя
     */
    @GetMapping("/artist/get_artist_quizzes")
    public ResponseEntity<?> getAllArtistByGenreId(@RequestParam(name = "genreId") Long genreId) {
        List<Quiz> quizList = quizRepository.findByArtGenreIdAndArtistIdIsNotNull(genreId);
        JsonArray jsonArray = new JsonArray();
        JsonObject mainJsonObject = new JsonObject();
        for (Quiz quiz : quizList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("quizId", quiz.getId());
            jsonObject.addProperty("artistId", quiz.getArtist().getId());
            jsonObject.addProperty("artistUrl", ARTIST_IMAGE_URL + quiz.getArtist().getImage());
            jsonArray.add(jsonObject);
        }
        mainJsonObject.add("artists", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }

    /**
     * Получение представителя по идентификатору
     * @param profileImageFile изображение представителя
     * @param bioImageFiles массив изображений статьи
     * @param addArticleRequest запрос на добавление статьи
     * @param addArtistRequest запрос на добавление представителя
     * @return "Представитель добавлен!"
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
    /**
     * Получение представителя по идентификатору
     * @param request запрос на добавление представителя
     * @return JSON-объект с данными представителя
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
