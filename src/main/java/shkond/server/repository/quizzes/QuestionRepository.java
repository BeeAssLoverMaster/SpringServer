package shkond.server.repository.quizzes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.quizzes.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> { }
