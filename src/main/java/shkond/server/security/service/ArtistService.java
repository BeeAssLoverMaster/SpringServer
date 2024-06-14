package shkond.server.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shkond.server.model.Artist;
import shkond.server.repository.ArtistRepository;

import java.util.List;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
}