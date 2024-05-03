package shkond.server.model.quizzes;

import jakarta.persistence.*;

@Entity
@Table(name = "question_images")
public class QuestionImage {
    @Id
    @Column(name = "question_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
