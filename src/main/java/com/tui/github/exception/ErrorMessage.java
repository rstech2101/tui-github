package com.tui.github.exception;

/**
 * ErrrorMessage class to provide the ErrorMessages and status to API
 * @author
 *
 */
public class ErrorMessage {
  private int status;
  private String message;

  public ErrorMessage(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }


  public String getMessage() {
    return message;
  }

}
