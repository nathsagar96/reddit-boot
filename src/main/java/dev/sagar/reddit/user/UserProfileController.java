package dev.sagar.reddit.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class UserProfileController {
  private final UserProfileService userProfileService;

  @PutMapping("/{username}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public UserProfileDto updateProfile(
      @RequestParam final String username, @Valid @RequestBody UserProfileDto profileDto) {
    return userProfileService.updateProfile(username, profileDto);
  }

  @GetMapping("/{username}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public UserProfileDto getProfile(@PathVariable final String username) {
    return userProfileService.getProfile(username);
  }
}
