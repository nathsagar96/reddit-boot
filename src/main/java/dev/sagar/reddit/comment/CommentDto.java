package dev.sagar.reddit.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentDto(
    Long id,
    @NotBlank(message = "Text is required") String text,
    Long postId,
    Long parentCommentId) {}
