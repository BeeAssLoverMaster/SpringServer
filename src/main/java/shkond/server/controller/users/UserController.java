package shkond.server.controller.users;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.quizzes.Quiz;
import shkond.server.model.users.Result;
import shkond.server.model.users.ResultId;
import shkond.server.model.users.User;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.quizzes.QuizRepository;
import shkond.server.repository.users.ResultRepository;
import shkond.server.repository.users.UserRepository;
import shkond.server.security.service.ImageService;
import shkond.server.security.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
/* Контроллер для пользователей */
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArtCategoryRepository artCategoryRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ResultRepository resultRepository;
    @Value("${image.profiles-dir}")
    private String profileDir;

    /**
     * Получение профиля текущего пользователя
     * @return JSON-объект с данными профиля
     */
    @GetMapping("/users/get_profile")
    public ResponseEntity<?> getUserProfile(){
        var user = userService.getCurrentUser();
        String imageName = "http://192.168.1.6:8080/api/image/profile/" + user.getImage();
        Optional<ArtCategory> artCategory = Optional.ofNullable(user.getArtCategory());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", user.getUsername());
        jsonObject.addProperty("bio", user.getBio());
        jsonObject.addProperty("profilePictureUrl", imageName);
        if (artCategory.isEmpty()) {
            jsonObject.addProperty("artCategory", 0);
        } else {
            jsonObject.addProperty("artCategory", artCategory.get().getId());
        }
        jsonObject.addProperty("points", user.getPoints());
        String jsonString = jsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }

    /**
     * Добавление изображения профиля пользователя
     * @param file изображение профиля
     * @return ответ сервера о результате операции
     * @throws IOException в случае ошибки ввода-вывода
     */
    @PostMapping("/users/add_img")
    public ResponseEntity<?> addImage(@RequestPart(value = "file") MultipartFile file) throws IOException {
        String imageName = imageService.saveImage(file, profileDir);
        User user = userService.getCurrentUser();
        user.setImage(imageName);
        userRepository.save(user);
        return ResponseEntity.ok("imagename: " + imageName + ", username: " + user.getUsername());
    }

    /**
     * Обновление категории искусства у пользователя
     * @param artCategoryId
     * @return ответ сервера о результате операции
     */
    @PutMapping("/users/update_art_category/{artCategoryId}")
    public ResponseEntity<?> updateProfileArtCategoryId(@PathVariable Long artCategoryId) {
        Optional<ArtCategory> artCategoryOptional = artCategoryRepository.findById(artCategoryId);
        if (!artCategoryOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Категория не найдена!");
        }
        User user = userService.getCurrentUser();
        user.setArtCategory(artCategoryOptional.get());
        userRepository.save(user);
        return ResponseEntity.ok("Категория успешно изменена!");
    }

    /**
     * Добавление очков пользователю за прохождение теста
     * @param points
     * @param quizId
     * @return ответ сервера о результате операции
     */
    @PutMapping("/users/add_points/{points}/{quizId}")
    public ResponseEntity<?> addPointsToUser(@PathVariable Integer points, @PathVariable Long quizId) {
        User user = userService.getCurrentUser();
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        ResultId resultId = new ResultId(user, quiz.get());
        if (resultRepository.existsById(resultId)) {
            Optional<Result> result = resultRepository.findById(resultId);
            if (points > result.get().getResult()) {
                user.setPoints(user.getPoints() + points - result.get().getResult());
                result.get().setResult(points);
                userRepository.save(user);
                resultRepository.save(result.get());
            }
        } else {
            Result result = new Result(
                    user,
                    quiz.get(),
                    points
            );
            user.setPoints(user.getPoints() + points);
            userRepository.save(user);
            resultRepository.save(result);
        }
        return ResponseEntity.ok("Очки зачислены");
    }
}

