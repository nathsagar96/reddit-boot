package dev.sagar.reddit.subreddit;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subreddits")
public class SubredditController {
  private final SubredditService subredditService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @SecurityRequirement(name = "bearerAuth")
  public SubredditDto createSubreddit(
      @RequestBody SubredditDto subredditDto, @RequestParam final String username) {
    return subredditService.createSubreddit(subredditDto, username);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<SubredditDto> getAllSubreddits() {
    return subredditService.getAllSubreddits();
  }

  @GetMapping("/{subredditId}")
  @ResponseStatus(HttpStatus.OK)
  public SubredditDto getSubredditById(@PathVariable final Long subredditId) {
    return subredditService.getSubredditById(subredditId);
  }

  @GetMapping("/name/{name}")
  public SubredditDto getSubredditByName(@PathVariable final String name) {
    return subredditService.getSubredditByName(name);
  }

  @PostMapping("/{subredditId}/join")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public String joinSubreddit(
      @PathVariable final Long subredditId, @RequestParam final String username) {
    return subredditService.joinSubreddit(subredditId, username);
  }

  @PostMapping("/{subredditId}/leave")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public String leaveSubreddit(
      @PathVariable final Long subredditId, @RequestParam final String username) {
    return subredditService.leaveSubreddit(subredditId, username);
  }

  @PostMapping("/{subredditId}/add-admin")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public String addAdmin(
      @PathVariable final Long subredditId, @RequestParam final String username) {
    return subredditService.addAdmin(subredditId, username);
  }

  @PostMapping("/{subredditId}/remove-admin")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public String removeAdmin(
      @PathVariable final Long subredditId, @RequestParam final String username) {
    return subredditService.removeAdmin(subredditId, username);
  }
}
