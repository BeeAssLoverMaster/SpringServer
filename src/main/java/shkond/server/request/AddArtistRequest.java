package shkond.server.request;

import lombok.Getter;

@Getter
/* Данные для добавления представителя */
public class AddArtistRequest {
    private Long artistId;
    private String artistName;
}
