package dev.sagar.reddit.vote;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

  private final VoteService voteService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String vote(@Valid @RequestBody VoteDto voteDto, @RequestParam final String username) {
    return voteService.vote(voteDto, username);
  }
}
