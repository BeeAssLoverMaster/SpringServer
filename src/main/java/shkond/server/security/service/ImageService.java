package shkond.server.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;



@Service
public class ImageService {

    public String saveImage(MultipartFile file, String uploadDir) {
        try {
            String encodeFileName = UUID.randomUUID().toString() + ".png";
            Path copyLocation = Paths.get(uploadDir + File.separator + encodeFileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return encodeFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}