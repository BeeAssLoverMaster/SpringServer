package shkond.server.model.quizzes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "question_images")
public class QuestionImage {
    @Id
    @Column(name = "question_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /* Отношение "многие-к-одному" между QuestionImage и Question */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    @Column(name = "image_name")
    private String image;
    public QuestionImage(){}
    public QuestionImage(Question question, String image) {
        this.question = question;
        this.image = image;
    }
}
