package dev.sagar.reddit.vote;

import dev.sagar.reddit.post.PostRepository;
import dev.sagar.reddit.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

  private final VoteRepository voteRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Transactional
  public String vote(VoteDto voteDto, String username) {
    var user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    var post =
        postRepository
            .findById(voteDto.postId())
            .orElseThrow(() -> new RuntimeException("Post not found"));

    var existingVote = voteRepository.findByPostAndUser(post, user);

    if (existingVote.isPresent()) {
      throw new RuntimeException("You have already voted on this post");
    }

    Vote vote = Vote.builder().voteType(voteDto.voteType()).post(post).user(user).build();

    voteRepository.save(vote);

    int voteChange = voteDto.voteType().getDirection();
    post.setVoteCount(post.getVoteCount() + voteChange);
    postRepository.save(post);

    return "Vote Recorded";
  }
}
