package shkond.server.request.quizzes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
/* Данные для добавления ответов*/
public class AnswerRequest {
    private String answer;
    @JsonProperty // Для корректной десериализации поля при работе с JSON
    private boolean isCorrect;
}
