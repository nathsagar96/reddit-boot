package dev.sagar.reddit.vote;

import lombok.Getter;

@Getter
public enum VoteType {
  UPVOTE(1),
  DOWNVOTE(-1);

  private final int direction;

  VoteType(int direction) {
    this.direction = direction;
  }
}
