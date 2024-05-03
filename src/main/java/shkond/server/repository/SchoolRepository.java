package shkond.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.Artist;
import shkond.server.model.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> { }