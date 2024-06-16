package shkond.server.controller.school;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.school.Program;
import shkond.server.model.school.School;
import shkond.server.model.school.SchoolWork;
import shkond.server.model.school.Teacher;
import shkond.server.repository.article.ArticleCategoryRepository;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.school.ProgramsRepository;
import shkond.server.repository.school.SchoolRepository;
import shkond.server.repository.school.SchoolWorkRepository;
import shkond.server.repository.school.TeacherRepository;
import shkond.server.request.school.AddSchoolRequest;
import shkond.server.security.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api")
/* Контроллер для школ */
public class SchoolController {
    @Autowired
    private SchoolWorkRepository schoolWorkRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ProgramsRepository programsRepository;
    @Autowired
    private ArtCategoryRepository artCategoryRepository;
    @Autowired
    private ImageService imageService;
    @Value("${image.schools-dir}")
    private String schoolDir;
    private final String TEACHER_IMAGE_URL = "http://192.168.1.6:8080/api/image/teacher/";
    private final String SCHOOL_IMAGE_URL = "http://192.168.1.6:8080/api/image/school/";
    /**
     * Получение списка всех школ
     * @return JSON-объект, содержащий все школы
     */
    @GetMapping("/school/get/all/schools")
    public ResponseEntity<?> getSchoolList() {
        List<School> schoolList = schoolRepository.findAll();
        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonSchoolArray = new JsonArray();
        for (School school : schoolList) {
            JsonObject jsonSchoolObject = new JsonObject();
            jsonSchoolObject.addProperty("schoolId", school.getId());
            jsonSchoolObject.addProperty("schoolName", school.getSchoolName());
            jsonSchoolObject.addProperty("artCategoryId", school.getArtCategory().getId());
            jsonSchoolObject.addProperty("artCategoryName", school.getArtCategory().getName());
            jsonSchoolObject.addProperty("description", school.getDescription());
            jsonSchoolObject.addProperty("city", school.getCity());
            jsonSchoolObject.addProperty("street", school.getStreet());
            jsonSchoolObject.addProperty("schoolImageName", SCHOOL_IMAGE_URL + school.getImageName() + ".png");
            JsonArray jsonProgramArray = new JsonArray();
            for (Program program : school.getPrograms()) {
                JsonObject jsonProgramObject = new JsonObject();
                jsonProgramObject.addProperty("programId", program.getId());
                jsonProgramObject.addProperty("programName", program.getProgramName());
                jsonProgramArray.add(jsonProgramObject);
            }
            jsonSchoolObject.add("programs", jsonProgramArray);
//            List<SchoolWork> schoolWorkList = schoolWorkRepository.findBySchoolId(school.getId());
//            JsonArray jsonSchoolWorkArray = new JsonArray();
//            for (SchoolWork schoolWork : schoolWorkList) {
//                JsonObject jsonSchoolWorkObject = new JsonObject();
//                jsonSchoolWorkObject.addProperty("schoolWorkId", schoolWork.getId());
//                jsonSchoolWorkObject.addProperty("schoolWorkImage", schoolWork.getImage());
//                jsonSchoolWorkObject.addProperty("author", schoolWork.getAuthor());
//                jsonSchoolWorkArray.add(jsonSchoolWorkObject);
//            }
//            jsonSchoolObject.add("schoolWorks", jsonSchoolWorkArray);
//            List<Teacher> teacherList = teacherRepository.findBySchoolId(school.getId());
//            JsonArray jsonTeacherArray = new JsonArray();
//            for (Teacher teacher : teacherList) {
//                JsonObject jsonTeacherObject = new JsonObject();
//                jsonTeacherObject.addProperty("teacherId", teacher.getId());
//                jsonTeacherObject.addProperty("teacherName", teacher.getTeacherName());
//                jsonTeacherObject.addProperty("teacherImage", teacher.getTeacherImage());
//                jsonTeacherArray.add(jsonTeacherObject);
//            }
//            jsonSchoolObject.add("teachers", jsonTeacherArray);
            jsonSchoolArray.add(jsonSchoolObject);
        }
        mainJsonObject.add("schools", jsonSchoolArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }

    /**
     * Добавление новой школы
     * @param schoolRequest запрос с данными для добавления школы
     * @param file изображение школы
     * @return Сообщение об успешном добавлении школы
     */
    @PostMapping("/school/add")
    public ResponseEntity<?> addSchool(
            @RequestPart("schoolRequest") AddSchoolRequest schoolRequest,
            @RequestPart("file") MultipartFile file) {

        // Сохранение изображения
        String imageName = imageService.saveImage(file, schoolDir); // Укажите правильный путь

        // Поиск категории искусства
        ArtCategory artCategory = artCategoryRepository.findById(schoolRequest.getArtCategoryId())
                .orElseThrow(() -> new RuntimeException("Art category not found"));

        // Поиск программ обучения
        List<Program> programs = programsRepository.findAllById(schoolRequest.getProgramsId());

        // Создание новой школы
        School school = new School(
                schoolRequest.getSchoolName(),
                artCategory,
                schoolRequest.getDescription(),
                schoolRequest.getCity(),
                schoolRequest.getStreet(),
                imageName,
                programs
        );

        // Сохранение школы
        schoolRepository.save(school);

        return ResponseEntity.ok("Школа успешно добавлена!");
    }

    @GetMapping("/school/get_teacher")
    public ResponseEntity<?> getTeachersBySchoolId(
            @RequestParam(name = "schoolId") Long schooldId
    ) {
        List<Teacher> teacherList = teacherRepository.findBySchoolId(schooldId);
        JsonArray jsonArray = new JsonArray();
        JsonObject mainJsonObject = new JsonObject();

        for (Teacher teacher : teacherList) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", teacher.getId());
            jsonObject.addProperty("schoolId", teacher.getSchool().getId());
            jsonObject.addProperty("teacherName", teacher.getTeacherName());
            jsonObject.addProperty("teacherImage", TEACHER_IMAGE_URL + teacher.getTeacherImage() + ".png");
            jsonArray.add(jsonObject);
        }
        mainJsonObject.add("teachers", jsonArray);
        String jsonString = mainJsonObject.toString();

        return ResponseEntity.ok(jsonString);
    }
}

