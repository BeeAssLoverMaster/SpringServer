package shkond.server.controller.users;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.users.User;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.users.UserRepository;
import shkond.server.security.service.ImageService;
import shkond.server.security.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Value("${image.profiles-dir}")
    private String profileDir;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ArtCategoryRepository artCategoryRepository;

    @GetMapping("/users/get_profile")
    public ResponseEntity<?> getUserProfile(){
        var user = userService.getCurrentUser();

        String imageName = "http://10.0.2.2:8080/api/image/profile/" + user.getImage();

        Optional<ArtCategory> artCategory = Optional.ofNullable(user.getArtCategory());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", user.getUsername());
        jsonObject.addProperty("bio", user.getBio());
        jsonObject.addProperty("profilePictureUrl", imageName);
        if (artCategory.isEmpty()) {
//            artCategory = artCategoryRepository.findById(1);
            jsonObject.addProperty("artCategory", 0);
        } else {
            jsonObject.addProperty("artCategory", artCategory.get().getId());
        }
        jsonObject.addProperty("points", user.getPoints());


        String jsonString = jsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }

    @PostMapping("/users/add_img")
    public ResponseEntity<?> addImage(@RequestPart(value = "file") MultipartFile file) throws IOException {

        String imageName = imageService.saveImage(file, profileDir);

        User user = userService.getCurrentUser();
        user.setImage(imageName);
        userRepository.save(user);


        return ResponseEntity.ok("imagename: " + imageName + ", username: " + user.getUsername());
    }
}
