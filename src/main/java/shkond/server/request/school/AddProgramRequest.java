package shkond.server.request.school;

import lombok.Getter;
import lombok.Setter;
import shkond.server.model.arts.ArtCategory;

@Getter
/* Данные для добавления программы обучения */
public class AddProgramRequest {
    private String programName;
    private Long artCategoryId;
}
