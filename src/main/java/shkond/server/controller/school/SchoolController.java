package shkond.server.controller.school;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.model.school.Program;
import shkond.server.model.school.School;
import shkond.server.model.school.SchoolWork;
import shkond.server.model.school.Teacher;
import shkond.server.repository.school.ProgramsRepository;
import shkond.server.repository.school.SchoolRepository;
import shkond.server.repository.school.SchoolWorkRepository;
import shkond.server.repository.school.TeacherRepository;
import shkond.server.request.school.AddSchoolAndTeacherRequest;

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
            jsonSchoolObject.addProperty("schoolImageName", school.getImageName());
            JsonArray jsonProgramArray = new JsonArray();
            for (Program program : school.getPrograms()) {
                JsonObject jsonProgramObject = new JsonObject();
                jsonProgramObject.addProperty("programId", program.getId());
                jsonProgramObject.addProperty("programName", program.getProgramName());
                jsonProgramArray.add(jsonProgramObject);
            }
            jsonSchoolObject.add("programs", jsonProgramArray);
            List<SchoolWork> schoolWorkList = schoolWorkRepository.findBySchoolId(school.getId());
            JsonArray jsonSchoolWorkArray = new JsonArray();
            for (SchoolWork schoolWork : schoolWorkList) {
                JsonObject jsonSchoolWorkObject = new JsonObject();
                jsonSchoolWorkObject.addProperty("schoolWorkId", schoolWork.getId());
                jsonSchoolWorkObject.addProperty("schoolWorkImage", schoolWork.getImage());
                jsonSchoolWorkObject.addProperty("author", schoolWork.getAuthor());
                jsonSchoolWorkArray.add(jsonSchoolWorkObject);
            }
            jsonSchoolObject.add("schoolWorks", jsonSchoolWorkArray);
            List<Teacher> teacherList = teacherRepository.findBySchoolId(school.getId());
            JsonArray jsonTeacherArray = new JsonArray();
            for (Teacher teacher : teacherList) {
                JsonObject jsonTeacherObject = new JsonObject();
                jsonTeacherObject.addProperty("teacherId", teacher.getId());
                jsonTeacherObject.addProperty("teacherName", teacher.getTeacherName());
                jsonTeacherObject.addProperty("teacherImage", teacher.getTeacherImage());
                jsonTeacherArray.add(jsonTeacherObject);
            }
            jsonSchoolObject.add("teachers", jsonTeacherArray);
            jsonSchoolArray.add(jsonSchoolObject);
        }
        mainJsonObject.add("schools", jsonSchoolArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }
}

