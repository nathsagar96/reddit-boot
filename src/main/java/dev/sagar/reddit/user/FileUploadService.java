package dev.sagar.reddit.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
  private final String UPLOAD_DIR = "uploads/";

  public String uploadAvatar(MultipartFile file, String username) throws IOException {
    Path uploadPath = Paths.get(UPLOAD_DIR);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    String filename = username + "_" + file.getOriginalFilename();
    Path filePath = uploadPath.resolve(filename);
    Files.copy(file.getInputStream(), filePath);
    return filePath.toString();
  }
}
