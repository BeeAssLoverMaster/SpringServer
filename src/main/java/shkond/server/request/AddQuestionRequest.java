package shkond.server.request;

import shkond.server.model.arts.ArtGenre;

import java.util.List;

public class AddQuestionRequest {
    private String question;
    private Integer questionValue;
    private List<AnswerRequest> answerRequestList;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getQuestionValue() {
        return questionValue;
    }

    public void setQuestionValue(Integer questionValue) {
        this.questionValue = questionValue;
    }

    public List<AnswerRequest> getAnswerRequestList() {
        return answerRequestList;
    }

    public void setAnswerRequestList(List<AnswerRequest> answerRequestList) {
        this.answerRequestList = answerRequestList;
    }
}
