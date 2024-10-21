package dev.sagar.reddit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileUploadController {
  private final FileUploadService fileUploadService;

  @PostMapping("/avatar")
  @ResponseStatus(HttpStatus.OK)
  public String uploadAvatar(
      @RequestParam("file") MultipartFile file, @RequestParam("username") final String username)
      throws IOException {
    return fileUploadService.uploadAvatar(file, username);
  }
}
