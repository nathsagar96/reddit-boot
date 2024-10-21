package dev.sagar.reddit.comment;

import dev.sagar.reddit.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
  Optional<CommentVote> findByCommentAndUser(Comment comment, User user);
}
