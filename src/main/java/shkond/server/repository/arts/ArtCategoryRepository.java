package shkond.server.repository.arts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.Artist;
import shkond.server.model.arts.ArtCategory;

@Repository
public interface ArtCategoryRepository extends JpaRepository<ArtCategory, Long> { }
