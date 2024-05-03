package shkond.server.controller.quizzes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.articles.Article;
import shkond.server.model.articles.ArticleCategory;
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.quizzes.Quiz;
import shkond.server.repository.quizzes.QuizRepository;
import shkond.server.request.AddArticleRequest;
import shkond.server.request.AddQuestionRequest;
import shkond.server.security.service.QuizService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @GetMapping("/quiz/get_all")
    public ResponseEntity<?> getAllQuizzes(){
        List<Quiz> quizList = quizRepository.findAll();

        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for (Quiz quiz : quizList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", quiz.getId());
            jsonObject.addProperty("artGenreId", quiz.getArtGenre().getId());
            jsonObject.addProperty("articleId", quiz.getArticle().getId());

            jsonArray.add(jsonObject);
        }

        mainJsonObject.add("quizzes", jsonArray);

        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

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

    @PostMapping("/quiz/add")
    public ResponseEntity<?> addQuiz(
            @RequestPart("articleFiles") MultipartFile[] articleFiles,
            @RequestPart("questionFiles") MultipartFile[] questionFiles,
            @RequestPart("articleRequest") AddArticleRequest articleRequest,
            @RequestPart("questionRequest") AddQuestionRequest[] questionRequest
            ) {
        Article article = quizService.addArticle(articleFiles, articleRequest);

        Quiz quiz = quizService.createQuiz(article);

        quizService.addQuestions(questionFiles, questionRequest, quiz);

        return ResponseEntity.ok("Ахуеешь");
    }
}
