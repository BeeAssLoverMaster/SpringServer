package shkond.server.repository.arts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.arts.ArtType;

import java.util.List;
@Repository
public interface ArtGenreRepository extends JpaRepository<ArtGenre, Long> {
    List<ArtGenre> findAllByArtTypeId(Long id);
}
