package dev.sagar.reddit.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostDto(
    Long id,
    @NotBlank(message = "Title is required") String title,
    String content,
    @NotBlank(message = "Subreddit is required") Long subredditId) {}
