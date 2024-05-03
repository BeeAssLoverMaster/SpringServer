package shkond.server.repository.arts;

import org.springframework.data.jpa.repository.JpaRepository;
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.arts.ArtType;

import java.util.List;

public interface ArtGenreRepository extends JpaRepository<ArtGenre, Long> {
    List<ArtGenre> findAllByArtTypeId(Long id);
}
