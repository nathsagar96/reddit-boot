package dev.sagar.reddit.subreddit;

import dev.sagar.reddit.post.Post;
import dev.sagar.reddit.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "subreddits")
public class Subreddit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @OneToMany(mappedBy = "subreddit")
  private List<Post> posts;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "subreddit_admin",
      joinColumns = @JoinColumn(name = "subreddit_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> admins;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "subreddit_user",
      joinColumns = @JoinColumn(name = "subreddit_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> subscribers;
}
