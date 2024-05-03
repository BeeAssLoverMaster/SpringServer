package shkond.server.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerRequest {
    private String answer;
    @JsonProperty
    private boolean isCorrect;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
