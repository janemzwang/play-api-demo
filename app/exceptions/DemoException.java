package exceptions;

/**
 * @author Kang Kang Wang
 */
public class DemoException extends Exception {
  private int httpStatus;

  public DemoException(int status) {
    httpStatus = status;
  }

  public DemoException(int status, String message) {
    super(message);
    httpStatus = status;
  }

  public int getHttpStatus() {
    return httpStatus;
  }
}
