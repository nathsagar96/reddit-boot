package dev.sagar.reddit.vote;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record VoteDto(
    @NotBlank(message = "Post ID is required") Long postId,
    @NotBlank(message = "Vote type is required") VoteType voteType) {}
