package shkond.server.request.quizzes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {
    private String answer;
    @JsonProperty
    private boolean isCorrect;
}
