package models.responses;

import models.BaseModel;

/**
 * @author Kang Kang Wang
 */
public class SingularResponse extends SuccessResponse {

  private BaseModel result;

  public SingularResponse(long start_time, long duration) {
    super(start_time, duration);
    this.setCount(1);
  }

  public SingularResponse setResult(BaseModel result) {
    this.result = result;
    return this;
  }
}