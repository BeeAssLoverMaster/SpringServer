package shkond.server.repository.quizzes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.quizzes.Answer;
import shkond.server.model.quizzes.QuestionImage;

import java.util.List;

@Repository
public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> {
    List<QuestionImage> findAllByQuestionId(Long id);
}
