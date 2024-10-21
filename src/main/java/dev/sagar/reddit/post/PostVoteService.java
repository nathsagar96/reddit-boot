package dev.sagar.reddit.post;

import dev.sagar.reddit.user.UserRepository;
import dev.sagar.reddit.vote.VoteType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostVoteService {

  private final PostVoteRepository postVoteRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Transactional
  public String vote(Long postId, String username, VoteType voteType) {
    var user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    var post =
        postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

    var existingVote = postVoteRepository.findByPostAndUser(post, user);

    if (existingVote.isPresent()) {
      throw new RuntimeException("You have already voted on this post");
    }

    PostVote postVote = PostVote.builder().voteType(voteType).post(post).user(user).build();

    postVoteRepository.save(postVote);

    int voteChange = voteType.getDirection();
    post.setVoteCount(post.getVoteCount() + voteChange);
    postRepository.save(post);

    return "Vote Recorded";
  }
}
