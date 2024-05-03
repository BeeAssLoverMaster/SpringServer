package shkond.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> { }
