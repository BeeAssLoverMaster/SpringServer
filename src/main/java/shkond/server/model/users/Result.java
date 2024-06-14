package shkond.server.model.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.quizzes.Quiz;

import java.io.Serializable;

@Entity
@Getter
@Setter
@IdClass(ResultId.class)
@Table(name = "results")
public class Result {
    @Id
    /* Отношение "многие-ко-многим" между Result и User */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    /* Отношение "многие-ко-многим" между Result и Quiz */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @Column(name = "result")
    private int result;
    public Result() {}
    public Result(User user, Quiz quiz, int result) {
        this.user = user;
        this.quiz = quiz;
        this.result = result;
    }
}

