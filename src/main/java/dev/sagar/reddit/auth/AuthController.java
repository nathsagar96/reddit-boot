package dev.sagar.reddit.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final PasswordResetService passwordResetService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public String register(@Valid @RequestBody RegisterRequest request) {
    return authService.registerUser(request);
  }

  @GetMapping("/verify-email/{token}")
  @ResponseStatus(HttpStatus.OK)
  public String verifyAccount(@PathVariable final String token) {
    return authService.verifyAccount(token);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
  }

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
