package dev.sagar.reddit.subreddit;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubredditDto(
    Long id,
    @NotBlank(message = "Name is required") String name,
    String description,
    int numberOfSubscribers) {}
