package dev.sagar.reddit.subreddit;

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
  public SubredditDto createSubreddit(
      @RequestBody SubredditDto subredditDto, @RequestParam final String username) {
    return subredditService.createSubreddit(subredditDto, username);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<SubredditDto> getAllSubreddits() {
    return subredditService.getAllSubreddits();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public SubredditDto getSubredditById(@PathVariable final Long id) {
    return subredditService.getSubredditById(id);
  }

  @GetMapping("/name/{name}")
  public SubredditDto getSubredditByName(@PathVariable final String name) {
    return subredditService.getSubredditByName(name);
  }

  @PostMapping("/{id}/join")
  @ResponseStatus(HttpStatus.OK)
  public String joinSubreddit(@PathVariable final Long id, @RequestParam final String username) {
    return subredditService.joinSubreddit(id, username);
  }

  @PostMapping("/{id}/leave")
  @ResponseStatus(HttpStatus.OK)
  public String leaveSubreddit(@PathVariable final Long id, @RequestParam final String username) {
    return subredditService.leaveSubreddit(id, username);
  }

  @PostMapping("/{id}/add-admin")
  public String addAdmin(@PathVariable final Long id, @RequestParam final String username) {
    return subredditService.addAdmin(id, username);
  }

  @PostMapping("/{id}/remove-admin")
  public String removeAdmin(@PathVariable final Long id, @RequestParam final String username) {
    return subredditService.removeAdmin(id, username);
  }
}
