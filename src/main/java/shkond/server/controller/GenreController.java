//package shkond.server.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class GenreController {
//    @Autowired
//    GenreRepository genreRepository;
//
//    @GetMapping("/genres/get")
//    public ResponseEntity<?> getGenresForCategory(@RequestParam(name = "category") Integer categoryId) {
//        return genreRepository.findById(categoryId)
//                .map(category -> ResponseEntity.ok(category.getCategory()))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//}
