package models.responses;

import models.BaseModel;

import java.util.List;

/**
 * @author Kang Kang Wang
 */
public class PluralResponse<T extends BaseModel> extends SuccessResponse {

  private List<T> results;

  public PluralResponse(long start_time, long duration) {
    super(start_time, duration);
  }

  public PluralResponse setResults(List<T> results) {
    this.results = results;
    this.setCount(results.size());
    return this;
  }
}
