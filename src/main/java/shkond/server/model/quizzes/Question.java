package shkond.server.model.quizzes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question", nullable = true)
    private String question;
    /* Отношение "многие-к-одному" между Question и Quiz */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @Column(name = "question_value", nullable = true)
    private Integer questionValue;
    public Question() {};
    public Question(String question, Quiz quiz, Integer questionValue) {
        this.question = question;
        this.quiz = quiz;
        this.questionValue = questionValue;
    }
}
