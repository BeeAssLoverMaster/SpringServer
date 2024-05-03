package shkond.server.dto;

public class ArtistDTO {
    private String artistFIO;
    private String imageUrl;

    public ArtistDTO(String artistFIO, String imageUrl) {
        this.artistFIO = artistFIO;
        this.imageUrl = imageUrl;
    }

    // Геттеры и сеттеры


    public String getArtistFIO() {
        return artistFIO;
    }

    public void setArtistFIO(String artistFIO) {
        this.artistFIO = artistFIO;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
