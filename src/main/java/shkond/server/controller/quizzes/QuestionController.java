package shkond.server.controller.quizzes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shkond.server.model.quizzes.Answer;
import shkond.server.model.quizzes.Question;
import shkond.server.model.quizzes.QuestionImage;
import shkond.server.repository.quizzes.AnswerRepository;
import shkond.server.repository.quizzes.QuestionImageRepository;
import shkond.server.repository.quizzes.QuestionRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
/* Контроллер для вопросов */
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionImageRepository questionImageRepository;
    private final String QUESTION_IMAGE_URL = "http://192.168.1.6:8080/api/image/question/";

    /**
     * Получение всех вопросов и ответов по идентификатору викторины
     * @param quizId
     * @return JSON-объект, содержащий все вопросы и ответы по данной викторине
     */
    @GetMapping("/question/get_all")
    public ResponseEntity<?> getAllQuestionAndAnswersByQuizId(@RequestParam(name = "quizId") Long quizId) {
        List<Question> questionList = questionRepository.findAllByQuizId(quizId);
        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for(Question question: questionList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("question", question.getQuestion());
            jsonObject.addProperty("questionValue", question.getQuestionValue());
            List<QuestionImage> questionImageList = questionImageRepository.findAllByQuestionId(question.getId());
            JsonArray questionImageJsonArray = new JsonArray();
            if (!questionImageList.isEmpty()) {
                for (QuestionImage questionImage : questionImageList) {
                    JsonObject questionImageJsonObject = new JsonObject();
                    questionImageJsonObject.addProperty("questionImage", QUESTION_IMAGE_URL + questionImage.getImage());
                    questionImageJsonArray.add(questionImageJsonObject);
                }
            }
            jsonObject.add("questionImages", questionImageJsonArray);
            List<Answer> answerList = answerRepository.findAllByQuestionId(question.getId());
            JsonArray answerJsonArray = new JsonArray();
            for (Answer answer : answerList) {
                JsonObject answerJsonObject = new JsonObject();
                answerJsonObject.addProperty("answer", answer.getAnswer());
                answerJsonObject.addProperty("isCorrect", answer.isCorrect());
                answerJsonArray.add(answerJsonObject);
            }
            jsonObject.add("answers", answerJsonArray);
            jsonArray.add(jsonObject);
        }
        mainJsonObject.add("questions", jsonArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
}

