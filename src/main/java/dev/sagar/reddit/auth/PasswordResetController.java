package dev.sagar.reddit.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class PasswordResetController {

  private final PasswordResetService passwordResetService;

  @PostMapping("/forgot-password")
  @ResponseStatus(HttpStatus.OK)
  public String forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
    return passwordResetService.generateResetToken(request);
  }

  @PostMapping("/reset-password")
  @ResponseStatus(HttpStatus.OK)
  public String resetPassword(@Valid @RequestBody PasswordResetRequest request) {
    return passwordResetService.resetPassword(request);
  }
}
