package shkond.server.controller.school;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.school.Program;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.school.ProgramsRepository;
import shkond.server.request.school.AddProgramRequest;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
/* Контроллер для программ обучения */
public class ProgramController {
    @Autowired
    private ProgramsRepository programsRepository;
    @Autowired
    private ArtCategoryRepository artCategoryRepository;

    /**
     * Получение списка всех программ обучения
     * @return JSON-объект, содержащий все программы обучения
     */
    @GetMapping("/program/get/all")
    public ResponseEntity<?> getProgramList() {
        List<Program> programList = programsRepository.findAll();
        JsonObject mainJsonObject = new JsonObject();
        JsonArray jsonProgramArray = new JsonArray();
        for (Program program : programList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", program.getId());
            jsonObject.addProperty("programName", program.getProgramName());
            jsonProgramArray.add(jsonObject);
        }
        mainJsonObject.add("programs", jsonProgramArray);
        String jsonString = mainJsonObject.toString();
        return ResponseEntity.ok(jsonString);
    }

    /**
     * Добавление программы обучения
     * @param request
     * @return ответ сервера о результате операции
     */
    @PostMapping("/program/add")
    public ResponseEntity<?> addProgram(@RequestBody AddProgramRequest request) {
        Optional<ArtCategory> artCategory = artCategoryRepository.findById(request.getArtCategoryId());
        Program addProgram = new Program(
                request.getProgramName(),
                artCategory.get()
        );
        programsRepository.save(addProgram);
        return ResponseEntity.ok("Программа обучения успешно добавлена!");
    }
}

