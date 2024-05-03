package shkond.server.repository.quizzes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.Artist;
import shkond.server.model.quizzes.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> { }
