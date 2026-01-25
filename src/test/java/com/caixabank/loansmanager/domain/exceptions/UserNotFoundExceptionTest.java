package com.caixabank.loansmanager.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

  @Test
  void constructor_ShouldSetMessage() {
    String message = "User not found";
    UserNotFoundException exception = new UserNotFoundException(message);
    assertEquals(message, exception.getMessage());
  }
}
