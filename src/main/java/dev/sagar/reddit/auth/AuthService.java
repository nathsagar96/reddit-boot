package dev.sagar.reddit.auth;

import dev.sagar.reddit.email.EmailService;
import dev.sagar.reddit.user.User;
import dev.sagar.reddit.user.UserRepository;
import dev.sagar.reddit.user.VerificationToken;
import dev.sagar.reddit.user.VerificationTokenRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final VerificationTokenRepository tokenRepository;
  private final EmailService emailService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public String registerUser(RegisterRequest request) {
    User user =
        User.builder()
            .username(request.username())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .build();

    userRepository.save(user);
    String token = generateAndSaveVerificationToken(user);
    emailService.sendVerificationEmail(request.email(), token);
    return "User Registration Successful. Please check your email for verification.";
  }

  private String generateAndSaveVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken =
        VerificationToken.builder()
            .token(token)
            .user(user)
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();

    tokenRepository.save(verificationToken);
    return token;
  }

  @Transactional
  public String verifyAccount(String token) {
    VerificationToken savedToken =
        tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid Token"));

    if (savedToken.getExpiresAt().isBefore(Instant.now())) {
      String newToken = generateAndSaveVerificationToken(savedToken.getUser());
      emailService.sendVerificationEmail(savedToken.getUser().getEmail(), newToken);
      throw new RuntimeException("Token has expired. New token has been sent to your email");
    }

    User user = savedToken.getUser();
    user.setEnabled(true);
    userRepository.save(user);
    return "Account Activated Successfully";
  }

  public LoginResponse login(LoginRequest request) {
    var auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));

    String token = jwtService.generateToken(auth.getName());
    return new LoginResponse(token);
  }
}
