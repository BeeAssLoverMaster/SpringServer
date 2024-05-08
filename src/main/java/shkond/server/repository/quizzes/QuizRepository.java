package shkond.server.repository.quizzes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.quizzes.Quiz;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByArtGenreId(Long id);
    List<Quiz> findAllByArtistId(Long id);
}
