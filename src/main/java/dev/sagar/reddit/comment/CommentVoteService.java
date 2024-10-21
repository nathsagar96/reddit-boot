package dev.sagar.reddit.comment;

import dev.sagar.reddit.exception.DuplicateVoteException;
import dev.sagar.reddit.exception.ResourceNotFoundException;
import dev.sagar.reddit.user.User;
import dev.sagar.reddit.user.UserRepository;
import dev.sagar.reddit.vote.VoteType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentVoteService {
  private final CommentRepository commentRepository;
  private final CommentVoteRepository commentVoteRepository;
  private final UserRepository userRepository;

  @Transactional
  public String voteOnComment(Long commentId, String username, VoteType voteType) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new ResourceNotFoundException("User not found with username: " + username));

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id: " + commentId));

    // Check if the user has already voted on this comment
    var existingVote = commentVoteRepository.findByCommentAndUser(comment, user);

    if (existingVote.isPresent()) {
      throw new DuplicateVoteException("You have already voted on this post");
    }

    CommentVote vote = CommentVote.builder().comment(comment).user(user).voteType(voteType).build();
    commentVoteRepository.save(vote);

    // Adjust the comment vote count based on the new vote
    comment.setVoteCount(comment.getVoteCount() + voteType.getDirection());
    commentRepository.save(comment);
    return "Vote Recorded";
  }
}
