package dev.sagar.reddit.comment;

import dev.sagar.reddit.post.Post;
import dev.sagar.reddit.post.PostRepository;
import dev.sagar.reddit.user.User;
import dev.sagar.reddit.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Transactional
  public CommentDto createComment(CommentDto commentDto, String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Post post =
        postRepository
            .findById(commentDto.postId())
            .orElseThrow(() -> new RuntimeException("Post not found"));

    Comment parentComment = null;
    if (commentDto.parentCommentId() != null) {
      parentComment =
          commentRepository
              .findById(commentDto.parentCommentId())
              .orElseThrow(() -> new RuntimeException("Parent comment not found"));
    }

    Comment comment =
        Comment.builder()
            .text(commentDto.text())
            .post(post)
            .user(user)
            .parentComment(parentComment)
            .build();

    Comment savedComment = commentRepository.save(comment);
    return toDto(savedComment);
  }

  public List<CommentDto> getCommentsByPost(Long postId) {
    return commentRepository.findAllByPostId(postId).stream().map(this::toDto).toList();
  }

  @Transactional
  public CommentDto editComment(Long commentId, CommentDto commentDto, String username) {
    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

    if (!comment.getUser().getUsername().equals(username)) {
      throw new RuntimeException("You do not have permission to update this comment");
    }

    comment.setText(commentDto.text());
    Comment updatedComment = commentRepository.save(comment);

    return toDto(updatedComment);
  }

  public void deleteComment(Long commentId, String username) {
    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

    if (!comment.getUser().getUsername().equals(username)) {
      throw new RuntimeException("You do not have permission to delete this comment");
    }

    commentRepository.delete(comment);
  }

  private CommentDto toDto(Comment comment) {
    return CommentDto.builder()
        .id(comment.getId())
        .text(comment.getText())
        .postId(comment.getPost().getId())
        .parentCommentId(comment.getParentComment().getId())
        .build();
  }
}
