package shkond.server.request.quizzes;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
/* Данные для добавления вопроса*/
public class AddQuestionRequest {
    private String question;
    private Integer questionValue;
    private List<AnswerRequest> answerRequestList;
    private MultipartFile questionImage;
}
