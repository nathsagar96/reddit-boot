package dev.sagar.reddit.exception;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
  private HttpStatus status;
  private String message;
  private Instant timestamp;
  private Map<String, String> subErrors;
}
