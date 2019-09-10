package ratelimiter;

import java.util.LinkedList;
import java.util.Queue;

public class QueueBasedRateLimiter {

  private final Queue<RequestInfo> queue;
  private final int limit;

  public QueueBasedRateLimiter(int limit) {
    this.limit = limit;
    this.queue = new LinkedList<>();
  }

  private static class RequestInfo {

    long timestamp;
  }

  public boolean allow() {
    if (this.queue.size() >= limit) {
      return true;
    }

    return false;
  }
}
