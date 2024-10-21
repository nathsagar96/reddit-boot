package dev.sagar.reddit.comment;

import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommentDto createComment(
      @Valid @RequestBody CommentDto commentDto, @RequestParam final String username) {
    return commentService.createComment(commentDto, username);
  }

  @PutMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  public CommentDto editComment(
      @PathVariable final Long commentId,
      @Valid @RequestBody CommentDto commentDto,
      @RequestParam final String username) {
    return commentService.editComment(commentId, commentDto, username);
  }

  @DeleteMapping("/{commentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteComment(@PathVariable final Long commentId, @RequestParam String username) {
    commentService.deleteComment(commentId, username);
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public List<CommentDto> getCommentsByPost(@RequestParam final Long postId) {
    return commentService.getCommentsByPost(postId);
  }
}
