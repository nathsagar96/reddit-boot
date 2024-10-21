package dev.sagar.reddit.exception;

public class AlreadyExistException extends RuntimeException {
  public AlreadyExistException(String message) {
    super(message);
  }
}
