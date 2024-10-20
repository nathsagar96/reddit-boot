package dev.sagar.reddit.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequest(
    @NotBlank(message = "Username is required") String username,
    @NotBlank(message = "Email is required") @Email String email,
    @NotBlank(message = "Password is required")
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message =
                "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password) {}
