package controllers;

/**
 * @author Kang Kang Wang
 */
public class PaginationContext {
  public int _pageSize, _pid;

  public PaginationContext(int pageSize, int pid) {
    _pageSize = pageSize;
    _pid = pid;
  }

  public int getStart() {
    return _pageSize * _pid;
  }
}
