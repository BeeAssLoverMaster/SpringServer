package shkond.server.repository.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.school.Program;
import shkond.server.model.school.School;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    List<Program> findAllById(Long id);
}