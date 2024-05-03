//package shkond.server.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//import shkond.server.repository.users.UserRepository;
//import shkond.server.security.service.ImageService;
//import shkond.server.security.service.UserService;
//
//import java.io.IOException;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/image")
//public class ImageController {
//    @Autowired
//    private UserService service ;
//    @Autowired
//    private ImageService imageService;
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private ProfileImagesRepository imageRepository;
//
//    @PostMapping("/profile_img")
//    public ResponseEntity<?> addImage(@RequestPart("image") MultipartFile file) throws IOException {
//        //TODO добавить проверку, что функция addImage отработала без ошибок
//
//        String imageName = imageService.saveImage(file);
//
////        ProfileImages image = new ProfileImages(imageName);
//        System.out.println("Зашли в addImage");
////        imageRepository.save(image);
//
//        var user = service.getCurrentUser();
////        user.setImageProfileId(image);
//        userRepository.save(user);
//
//        return ResponseEntity.ok("imagename: " + imageName + ", username: " + user.getUsername());
//    }
//}
