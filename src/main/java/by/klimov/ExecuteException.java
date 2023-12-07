package by.klimov;

public class ExecuteException extends RuntimeException {

  public static final String DEFAULT_ERROR_MESSAGE = "Execution exception";

  public ExecuteException(Exception e) {
    super(DEFAULT_ERROR_MESSAGE, e);
  }
}
