package dev.sagar.reddit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
  private final UserRepository userRepository;

  @Transactional
  public UserProfileDto updateProfile(String username, UserProfileDto profileDto) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setBio(profileDto.bio());

    if (!profileDto.avatarUrl().isBlank()) {
      user.setAvatarUrl(profileDto.avatarUrl());
    }

    userRepository.save(user);
    return toDto(user);
  }

  public UserProfileDto getProfile(String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return toDto(user);
  }

  private UserProfileDto toDto(User user) {
    return UserProfileDto.builder()
        .username(user.getUsername())
        .avatarUrl(user.getAvatarUrl())
        .bio(user.getBio())
        .build();
  }
}
