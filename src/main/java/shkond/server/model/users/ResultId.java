package shkond.server.model.users;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import shkond.server.model.quizzes.Quiz;

import java.io.Serializable;

public class ResultId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public ResultId() {}

    public ResultId(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
    }
}
