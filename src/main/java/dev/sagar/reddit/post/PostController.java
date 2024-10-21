package dev.sagar.reddit.post;

import dev.sagar.reddit.vote.VoteType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;
  private final PostVoteService postVoteService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @SecurityRequirement(name = "bearerAuth")
  public PostDto createPost(
      @Valid @RequestBody PostDto postDto, @RequestParam final String username) {
    return postService.createPost(postDto, username);
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDto getPostById(@PathVariable final Long postId) {
    return postService.getPostById(postId);
  }

  @GetMapping("/by-subreddit/{subredditId}")
  @ResponseStatus(HttpStatus.OK)
  public List<PostDto> getPostsBySubreddit(@PathVariable final Long subredditId) {
    return postService.getPostsBySubreddit(subredditId);
  }

  @GetMapping("/by-user/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public List<PostDto> getPostsByUser(@PathVariable final Long userId) {
    return postService.getPostsByUser(userId);
  }

  @PutMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "bearerAuth")
  public PostDto editPost(
      @PathVariable final Long postId,
      @Valid @RequestBody PostDto postDto,
      @RequestParam final String username) {
    return postService.editPost(postId, postDto, username);
  }

  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @SecurityRequirement(name = "bearerAuth")
  public void deletePost(@PathVariable final Long postId, @RequestParam final String username) {
    postService.deletePost(postId, username);
  }

  @PostMapping("/{postId}/vote")
  @ResponseStatus(HttpStatus.CREATED)
  @SecurityRequirement(name = "bearerAuth")
  public String voteOnPost(
      @PathVariable final Long postId,
      @RequestParam final String username,
      @RequestParam VoteType voteType) {
    return postVoteService.vote(postId, username, voteType);
  }
}
