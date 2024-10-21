package dev.sagar.reddit.exception;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Object handleResourceNotFoundException(ResourceNotFoundException ex) {
    return ApiError.builder()
        .status(HttpStatus.NOT_FOUND)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(DuplicateVoteException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Object handleDuplicateVoteException(DuplicateVoteException ex) {
    return ApiError.builder()
        .status(HttpStatus.CONFLICT)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(AlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Object handleAlreadyExistException(AlreadyExistException ex) {
    return ApiError.builder()
        .status(HttpStatus.CONFLICT)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(InvalidTokenException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Object handleInvalidTokenException(InvalidTokenException ex) {
    return ApiError.builder()
        .status(HttpStatus.UNAUTHORIZED)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Object handleUnauthorizedException(UnauthorizedException ex) {
    return ApiError.builder()
        .status(HttpStatus.UNAUTHORIZED)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return ApiError.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message("Invalid request")
        .timestamp(Instant.now())
        .subErrors(errors)
        .build();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Object handleIllegalArgumentException(IllegalArgumentException ex) {
    return ApiError.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Object handleUsernameNotFoundException(UsernameNotFoundException ex) {
    return ApiError.builder()
        .status(HttpStatus.NOT_FOUND)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public Object handleAccessDeniedException(AccessDeniedException ex) {
    return ApiError.builder()
        .status(HttpStatus.FORBIDDEN)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Object handleAuthenticationCredentialsNotFoundException(
      AuthenticationCredentialsNotFoundException ex) {
    return ApiError.builder()
        .status(HttpStatus.UNAUTHORIZED)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Object handleBadCredentialsException(BadCredentialsException ex) {
    return ApiError.builder()
        .status(HttpStatus.UNAUTHORIZED)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Object handleException(Exception ex) {
    return ApiError.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .message(ex.getMessage())
        .timestamp(Instant.now())
        .build();
  }
}
