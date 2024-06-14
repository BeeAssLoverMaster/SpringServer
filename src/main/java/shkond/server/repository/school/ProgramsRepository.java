package shkond.server.repository.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.school.Program;

import java.util.List;

@Repository
public interface ProgramsRepository extends JpaRepository<Program, Long> {
}