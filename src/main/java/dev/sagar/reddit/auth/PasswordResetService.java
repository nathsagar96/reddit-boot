package dev.sagar.reddit.auth;

import dev.sagar.reddit.email.EmailService;
import dev.sagar.reddit.user.PasswordResetToken;
import dev.sagar.reddit.user.PasswordResetTokenRepository;
import dev.sagar.reddit.user.User;
import dev.sagar.reddit.user.UserRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

  private final UserRepository userRepository;
  private final PasswordResetTokenRepository resetTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;

  @Transactional
  public String generateResetToken(ForgotPasswordRequest request) {
    User user =
        userRepository
            .findByEmail(request.email())
            .orElseThrow(
                () -> new RuntimeException("User not found with email: " + request.email()));

    String token = generateAndSavePasswordResetToken(user);
    emailService.sendResetPasswordEmail(user.getEmail(), token);
    return "Password reset email sent. Please check your inbox.";
  }

  @Transactional
  public String resetPassword(PasswordResetRequest request) {
    PasswordResetToken resetToken =
        resetTokenRepository
            .findByToken(request.token())
            .orElseThrow(() -> new RuntimeException("Invalid Token"));

    if (resetToken.getExpiresAt().isBefore(Instant.now())) {
      String newToken = generateAndSavePasswordResetToken(resetToken.getUser());
      emailService.sendResetPasswordEmail(resetToken.getUser().getEmail(), newToken);
      throw new RuntimeException("Token has expired. New token has been sent to your email");
    }

    User user = resetToken.getUser();
    user.setPassword(passwordEncoder.encode(request.newPassword()));
    userRepository.save(user);

    return "Password reset successfully.";
  }

  private String generateAndSavePasswordResetToken(User user) {
    String token = UUID.randomUUID().toString();
    PasswordResetToken resetToken =
        PasswordResetToken.builder()
            .token(token)
            .user(user)
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();

    resetTokenRepository.save(resetToken);
    return token;
  }
}
