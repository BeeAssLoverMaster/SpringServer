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
import shkond.server.model.quizzes.Quiz;
import shkond.server.repository.quizzes.QuizRepository;
import shkond.server.request.articles.AddArticleRequest;
import shkond.server.request.quizzes.AddQuestionRequest;
import shkond.server.security.service.QuizService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    /* Никем не используется */
    @GetMapping("/quiz/get_all")
    public ResponseEntity<?> getAllQuizzes() {
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

    /* Никем не используется */
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

    /* Web:
     * ArticleAndQuiz*/
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

        return ResponseEntity.ok("Статья и викторина сохранены");
    }
}
