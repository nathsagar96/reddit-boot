package dev.sagar.reddit.user;

import lombok.Builder;

@Builder
public record UserProfileDto(String username, String bio, String avatarUrl) {}
