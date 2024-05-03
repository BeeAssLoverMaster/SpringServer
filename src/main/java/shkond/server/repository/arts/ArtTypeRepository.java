package shkond.server.repository.arts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shkond.server.model.arts.ArtType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtTypeRepository extends JpaRepository<ArtType, Long> {
    List<ArtType> findAllByArtCategoryId(Long id);
}
