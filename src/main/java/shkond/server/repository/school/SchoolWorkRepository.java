package shkond.server.repository.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.school.Program;
import shkond.server.model.school.SchoolWork;

import java.util.List;

@Repository
public interface SchoolWorkRepository extends JpaRepository<SchoolWork, Long> {
    List<SchoolWork> findBySchoolId(Long id);
}