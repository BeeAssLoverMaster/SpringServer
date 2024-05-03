//package shkond.server.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import shkond.server.dto.ArticleDTO;
//import shkond.server.model.article.Article;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
//public class ArticleController {
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @PostMapping("/articles/add")
//    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO articleDTO) {
//        String fullContent = String.join("\n", articleDTO.getTexts());
//        Article article = new Article(fullContent);
//        return ResponseEntity.ok(articleRepository.save(article));
//    }
//}
