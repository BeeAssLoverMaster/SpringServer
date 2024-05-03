package shkond.server.request;

import org.springframework.web.multipart.MultipartFile;

public class ArtistRequest {

    String artistFIO;
    MultipartFile artistImg;

    public String getArtistFIO() {
        return artistFIO;
    }

    public void setArtistFIO(String artistFIO) {
        this.artistFIO = artistFIO;
    }

    public MultipartFile getArtistImg() {
        return artistImg;
    }

    public void setArtistImg(MultipartFile artistImg) {
        this.artistImg = artistImg;
    }
}
