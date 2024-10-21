package dev.sagar.reddit.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileUploadController {
  private final FileUploadService fileUploadService;

  @PostMapping("/avatar")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public String uploadAvatar(
      @RequestParam("file") MultipartFile file, @RequestParam("username") final String username)
      throws IOException {
    return fileUploadService.uploadAvatar(file, username);
  }
}
