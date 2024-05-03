//package shkond.server.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import shkond.server.dto.ArtistDTO;
//import shkond.server.model.Artist;
//import shkond.server.repository.ArtistRepository;
//import shkond.server.security.service.ImageService;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class ArtistController {
//
//    @Autowired
//    ArtistRepository artistRepository;
//
////    @Autowired
////    Prof imageRepository;
//
//    @Autowired
//    ImageService imageService;
//
//    @GetMapping("/artist")
//    public ResponseEntity<?> getAllArtist() {
//        return ResponseEntity.ok(artistRepository.findAll());
//    }
//
//    @PostMapping(value = "/artist/add", consumes = {"multipart/form-data"})
//    public ResponseEntity<?> addArtist(@RequestParam("artistFIO") String artistFIO, @RequestParam("artistImg") MultipartFile artistImg) {
//        String imageName = imageService.saveImage(artistImg);
//
//        Image image = new Image(imageName, "artist_img");
//        System.out.println("Зашли в addImage");
////        imageRepository.save(image);
//
//        Artist artist = new Artist(artistFIO, image);
//
//
//
//        artistRepository.save(artist);
//
//        return ResponseEntity.ok(new ArtistDTO(artistFIO, imageName));
//    }
//}
