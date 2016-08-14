package models.responses;

/**
 * @author Kang Kang Wang
 */
public class SuccessResponse extends BaseResponse {

  private int count;

  public String message;

  public SuccessResponse(long start_time, long duration) {
    super(start_time, duration);
  }

  public SuccessResponse setCount(int count) {
    this.count = count;
    return this;
  }
}
