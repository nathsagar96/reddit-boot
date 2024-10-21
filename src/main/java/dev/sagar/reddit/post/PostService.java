package dev.sagar.reddit.post;

import dev.sagar.reddit.exception.ResourceNotFoundException;
import dev.sagar.reddit.exception.UnauthorizedException;
import dev.sagar.reddit.subreddit.SubredditRepository;
import dev.sagar.reddit.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final SubredditRepository subredditRepository;
  private final UserRepository userRepository;

  @Transactional
  public PostDto createPost(PostDto postDto, String username) {
    var user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new ResourceNotFoundException("User not found with username: " + username));

    var subreddit =
        subredditRepository
            .findById(postDto.subredditId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Subreddit not found with id: " + postDto.subredditId()));

    Post post =
        Post.builder()
            .title(postDto.title())
            .content(postDto.content())
            .subreddit(subreddit)
            .user(user)
            .build();

    Post savedPost = postRepository.save(post);
    return toDto(savedPost);
  }

  public List<PostDto> getPostsBySubreddit(Long subredditId) {
    return postRepository.findAllBySubredditId(subredditId).stream().map(this::toDto).toList();
  }

  public List<PostDto> getPostsByUser(Long userId) {
    return postRepository.findAllByUserId(userId).stream().map(this::toDto).toList();
  }

  public PostDto getPostById(Long postId) {
    return postRepository
        .findById(postId)
        .map(this::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
  }

  @Transactional
  public void deletePost(Long postId, String username) {
    var post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

    if (!post.getUser().getUsername().equals(username)) {
      throw new UnauthorizedException("You do not have permission to delete this post");
    }

    postRepository.delete(post);
  }

  @Transactional
  public PostDto editPost(Long postId, PostDto postDto, String username) {
    var post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

    if (!post.getUser().getUsername().equals(username)) {
      throw new UnauthorizedException("You do not have permission to edit this post");
    }

    post.setContent(postDto.content());
    Post updatedPost = postRepository.save(post);

    return toDto(updatedPost);
  }

  private PostDto toDto(Post post) {
    return PostDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .subredditId(post.getSubreddit().getId())
        .build();
  }
}
