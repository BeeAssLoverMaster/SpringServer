package shkond.server.controller.quizzes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.articles.Article;
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.quizzes.Question;
import shkond.server.model.quizzes.Quiz;
import shkond.server.model.users.Result;
import shkond.server.model.users.ResultId;
import shkond.server.model.users.User;
import shkond.server.repository.article.ArticleImageRepository;
import shkond.server.repository.article.ArticleRepository;
import shkond.server.repository.quizzes.QuestionRepository;
import shkond.server.repository.quizzes.QuizRepository;
import shkond.server.repository.users.ResultRepository;
import shkond.server.request.articles.AddArticleRequest;
import shkond.server.request.quizzes.AddQuestionRequest;
import shkond.server.security.service.QuizService;
import shkond.server.security.service.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
/* Контроллер для связи статей и тестов */
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleImageRepository articleImageRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private QuizService quizService;
    private final String ARTICLE_IMAGE_URL = "http://192.168.1.6:8080/api/image/article/";
    private final String ARTIST_IMAGE_URL = "http://192.168.1.6:8080/api/image/artist/";
    /**
     * Получение всех статей и жанров по идентификатору жанра искусства
     * @param genreId
     * @return
     */
    @GetMapping("/quiz/get")
    public ResponseEntity<?> getQuizzesByGenreId(@RequestParam(name = "genreId") Long genreId) {
        List<Quiz> quizList = quizRepository.findAllByArtGenreId(genreId);
        JsonArray jsonArray = new JsonArray();
        JsonObject mainJsonObject = new JsonObject();
        for (Quiz quiz : quizList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", quiz.getId());
            jsonObject.addProperty("artGenreId", quiz.getArtGenre().getId());
            jsonObject.addProperty("articleId", quiz.getArticle().getId());
            jsonObject.addProperty("articleTitle", quiz.getArticle().getTitle());
            jsonArray.add(jsonObject);
        }
        mainJsonObject.add("quizzes", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Получение всех исторических статей по жанру
     * @param genreId
     * @return JSON-объект, содержащий все исторические статьи данного жанра искусства
     */
    @GetMapping("/quiz/get_by_genre/history_article")
    public ResponseEntity<?> getAllHistoryArticle(@RequestParam(name = "genreId") Long genreId) {
        List<Quiz> quizList = quizRepository.findByArtGenreIdAndArtistIdIsNull(genreId);
        JsonArray jsonArray = new JsonArray();
        JsonObject mainJsonObject = new JsonObject();
        for (Quiz quiz : quizList) {
            if (quiz.getArticle().getArticleCategory().getId() == 2L) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("quizId", quiz.getId());
                jsonObject.addProperty("title", quiz.getArticle().getTitle());
                jsonArray.add(jsonObject);
            }
        }
        mainJsonObject.add("historyArticles", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Получение всех статей с техниками исполнения и стилей в рамках жанра искусства
     * @param genreId
     * @return JSON-объект, содержащий все статьи данного жанра искусства
     */
    @GetMapping("/quiz/get_by_genre/technique_article")
    public ResponseEntity<?> getAllTechniqueArticle(@RequestParam(name = "genreId") Long genreId) {
        List<Quiz> quizList = quizRepository.findByArtGenreIdAndArtistIdIsNull(genreId);
        JsonArray jsonArray = new JsonArray();
        JsonObject mainJsonObject = new JsonObject();
        for (Quiz quiz : quizList) {
            if (quiz.getArticle().getArticleCategory().getId() == 3L) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("quizId", quiz.getId());
                jsonObject.addProperty("title", quiz.getArticle().getTitle());
                jsonArray.add(jsonObject);
            }
        }
        mainJsonObject.add("techniqueArticles", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
    /**
     * Получение статьи по идентификатору теста
     * @param quizId
     * @return JSON-объект, содержащий информацию о статье и тесте
     */
    @GetMapping("/quiz/get_by_quiz/article")
    public ResponseEntity<?> getArticleByQuizId(@RequestParam(name = "quizId") Long quizId) {
        User user = userService.getCurrentUser();
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        List<Question> questionList = questionRepository.findAllByQuizId(quizId);
        ResultId resultId = new ResultId(user, quiz.get());
        Optional<Result> result = resultRepository.findById(resultId);
        if (!quiz.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        int totalPoints = 0;
        Optional<Article> article = articleRepository.findById(quiz.get().getArticle().getId());
        if (!article.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<ArticleImage> articleImageList = articleImageRepository.findAllByArticleId(article.get().getId());
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        int fakeResult;
        if (result.isEmpty()) {
            fakeResult = 0;
        } else {
            fakeResult = result.get().getResult();
        }
        jsonObject.addProperty("quizId", quiz.get().getId());
        long artistId = quiz.get().getArtist() != null ? quiz.get().getArtist().getId() : -1;
        jsonObject.addProperty("artistId", artistId);
        jsonObject.addProperty("artistImage", artistId != -1 ? ARTIST_IMAGE_URL + quiz.get().getArtist().getImage() : "");
        jsonObject.addProperty("title", article.get().getTitle());
        jsonObject.addProperty("text", article.get().getText());
        jsonObject.addProperty("userPoints", fakeResult);
        for(Question question: questionList) {
            totalPoints += question.getQuestionValue();
        }
        jsonObject.addProperty("totalPoints", totalPoints);
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
     * Добавление викторины
     * @param articleFiles массив файлов статьи
     * @param articleRequest запрос на добавление статьи
     * @param questionFiles массив файлов вопросов
     * @param questionRequest запрос на добавление вопросов
     * @return "Статья и тест сохранены!"
     */
    @PostMapping("/quiz/add")
    public ResponseEntity<?> addQuiz(
            @RequestPart(value = "articleFiles", required = false) MultipartFile[] articleFiles,
            @RequestPart("articleRequest") AddArticleRequest articleRequest,
            @RequestPart(value = "questionFiles", required = false) MultipartFile[] questionFiles,
            @RequestPart("questionRequest") AddQuestionRequest[] questionRequest
    ) {
        Article article = quizService.addArticle(articleFiles, articleRequest);
        Quiz quiz = quizService.createQuiz(article);
        quizService.addQuestions(questionFiles, questionRequest, quiz);
        return ResponseEntity.ok("Статья и тест сохранены!");
    }
}
