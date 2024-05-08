package shkond.server.request.quizzes;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddQuestionRequest {
    private String question;
    private Integer questionValue;
    private List<AnswerRequest> answerRequestList;
    private MultipartFile questionImage;
}
