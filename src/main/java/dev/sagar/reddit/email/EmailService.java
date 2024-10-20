package dev.sagar.reddit.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  public void sendVerificationEmail(String recipientEmail, String token) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(recipientEmail);
    message.setSubject("Please Verify Your Email Address");
    message.setText(
        "To verify your email address, please click on the following link: "
            + "http://localhost:8080/api/auth/verify-email/"
            + token);
    mailSender.send(message);
  }

  public void sendResetPasswordEmail(String recipientEmail, String token) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(recipientEmail);
    message.setSubject("Password Reset Request");
    message.setText(
        "To reset your password, please click on the following link: "
            + "http://localhost:8080/api/auth/reset-password?token="
            + token);
    mailSender.send(message);
  }
}
