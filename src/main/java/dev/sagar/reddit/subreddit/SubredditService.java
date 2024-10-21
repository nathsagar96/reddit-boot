package dev.sagar.reddit.subreddit;

import dev.sagar.reddit.user.User;
import dev.sagar.reddit.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubredditService {
  private final SubredditRepository subredditRepository;
  private final UserRepository userRepository;

  @Transactional
  public SubredditDto createSubreddit(SubredditDto subredditDto, String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Subreddit subreddit =
        Subreddit.builder()
            .name(subredditDto.name())
            .description(subredditDto.description())
            .build();

    // Assign the creator as an admin
    subreddit.getAdmins().add(user);

    Subreddit savedSubreddit = subredditRepository.save(subreddit);
    return toDto(savedSubreddit);
  }

  public String addAdmin(Long subredditId, String username) {
    Subreddit subreddit = subredditRepository.findById(subredditId)
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Add the user as an admin if they're not already an admin
    if (!subreddit.getAdmins().contains(user)) {
      subreddit.getAdmins().add(user);
      subredditRepository.save(subreddit);
    }

    return "Admin added successfully";
  }

  public String removeAdmin(Long subredditId, String username) {
    Subreddit subreddit = subredditRepository.findById(subredditId)
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Remove the user from admins if they are currently an admin
    subreddit.getAdmins().remove(user);
    subredditRepository.save(subreddit);

    return "Admin removed successfully";
  }


  public List<SubredditDto> getAllSubreddits() {
    return subredditRepository.findAll().stream().map(this::toDto).toList();
  }

  public SubredditDto getSubredditById(Long id) {
    Subreddit subreddit =
        subredditRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));
    return toDto(subreddit);
  }

  public SubredditDto getSubredditByName(String name) {
    Subreddit subreddit =
        subredditRepository
            .findByName(name)
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));
    return toDto(subreddit);
  }

  @Transactional
  public String joinSubreddit(Long subredditId, String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Subreddit subreddit =
        subredditRepository
            .findById(subredditId)
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));

    if (!subreddit.getSubscribers().contains(user)) {
      subreddit.getSubscribers().add(user);
      subredditRepository.save(subreddit);
    }
    return "Subreddit joined";
  }

  @Transactional
  public String leaveSubreddit(Long subredditId, String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Subreddit subreddit =
        subredditRepository
            .findById(subredditId)
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));

    subreddit.getSubscribers().remove(user);
    subredditRepository.save(subreddit);

    return "Subreddit left";
  }

  private SubredditDto toDto(Subreddit subreddit) {
    return SubredditDto.builder()
        .id(subreddit.getId())
        .name(subreddit.getName())
        .description(subreddit.getDescription())
        .numberOfSubscribers(subreddit.getSubscribers().size())
        .build();
  }
}
