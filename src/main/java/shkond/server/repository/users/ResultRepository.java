package shkond.server.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import shkond.server.model.quizzes.Quiz;
import shkond.server.model.users.Result;
import shkond.server.model.users.ResultId;

@Repository
public interface ResultRepository extends JpaRepository<Result, ResultId> {
}
