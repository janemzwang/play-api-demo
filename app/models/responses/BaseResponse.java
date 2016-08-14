package models.responses;

/**
 * @author Kang Kang Wang
 */
public abstract class BaseResponse {

  class DebugInfo {

    private long start_time;
    private long duration;

    DebugInfo(long start_time, long duration) {
      this.start_time = start_time;
      this.duration = duration;
    }
  }

  private DebugInfo debug_info;

  protected BaseResponse(long start_time, long duration) {
    this.debug_info = new DebugInfo(start_time, duration);
  }
}
