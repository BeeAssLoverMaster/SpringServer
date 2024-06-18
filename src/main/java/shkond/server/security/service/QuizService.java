package shkond.server.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.Artist;
import shkond.server.model.articles.Article;
import shkond.server.model.articles.ArticleCategory;
import shkond.server.model.articles.ArticleImage;
import shkond.server.model.arts.ArtGenre;
import shkond.server.model.quizzes.Answer;
import shkond.server.model.quizzes.Question;
import shkond.server.model.quizzes.QuestionImage;
import shkond.server.model.quizzes.Quiz;
import shkond.server.repository.article.ArticleCategoryRepository;
import shkond.server.repository.article.ArticleImageRepository;
import shkond.server.repository.article.ArticleRepository;
import shkond.server.repository.arts.ArtGenreRepository;
import shkond.server.repository.quizzes.AnswerRepository;
import shkond.server.repository.quizzes.QuestionImageRepository;
import shkond.server.repository.quizzes.QuestionRepository;
import shkond.server.repository.quizzes.QuizRepository;
import shkond.server.request.articles.AddArticleRequest;
import shkond.server.request.quizzes.AddQuestionRequest;
import shkond.server.request.quizzes.AnswerRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ArtGenreRepository artGenreRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;
    @Autowired
    private ArticleImageRepository articleImageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionImageRepository questionImageRepository;
    @Value("${image.articles-dir}")
    private String assetDir;

    @Value("${image.questions-dir}")
    private String questionDir;

    public Article addArticle(MultipartFile[] files, AddArticleRequest request) {
        Optional<ArtGenre> artGenreOptional = request.getGenreId() != null ?
                artGenreRepository.findById(request.getGenreId()) :
                Optional.empty();
        Optional<ArticleCategory> articleCategoryOptional = request.getArticleCategoryId() != null ?
                articleCategoryRepository.findById(request.getArticleCategoryId()) :
                Optional.empty();

        Article addArticle = new Article(
                request.getTitle(),
                request.getText(),
                articleCategoryOptional.orElse(null),  // Use orElse to handle Optional properly
                artGenreOptional.orElse(null)
        );

        Article article = articleRepository.save(addArticle);
        int count = 0;

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String imageName = imageService.saveImage(file, assetDir);

                ArticleImage image = new ArticleImage(imageName, article, request.getDescription().get(count));
                count++;
                articleImageRepository.save(image);
            }
        }

        return article;
    }

    public Quiz createQuiz(Article article) {

        Quiz quiz = new Quiz(
                article.getArtGenre(),
                article
        );

        return quizRepository.save(quiz);
    }

    public Quiz createQuiz(Article article, Artist artist) {

        Quiz quiz = new Quiz(
                article.getArtGenre(),
                article,
                artist
        );

        return quizRepository.save(quiz);
    }

    public void addQuestions(
            AddQuestionRequest[] questionRequestsList,
            Quiz quiz,
            MultipartFile[] questionFiles) {

        int fileIndex = 0;
        for (AddQuestionRequest request : questionRequestsList) {
            Question question = new Question(
                    request.getQuestion(),
                    quiz,
                    request.getQuestionValue()
            );

            Question savedQuestion = questionRepository.save(question);

            for (AnswerRequest answerReq : request.getAnswerRequestList()) {
                Answer answer = new Answer(
                        savedQuestion,
                        answerReq.getAnswer(),
                        answerReq.isCorrect()
                );

                answerRepository.save(answer);
            }

            // Сохранение изображений, если они есть
            if (questionFiles != null && fileIndex < questionFiles.length && questionFiles[fileIndex] != null && !questionFiles[fileIndex].isEmpty()) {
                String imageName = imageService.saveImage(questionFiles[fileIndex], questionDir);
                QuestionImage image = new QuestionImage(savedQuestion, imageName);
                questionImageRepository.save(image);
                fileIndex++;
            }
        }
    }



}
