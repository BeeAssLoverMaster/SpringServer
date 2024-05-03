package shkond.server.repository.quizzes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.quizzes.QuestionImage;

@Repository
public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> { }
