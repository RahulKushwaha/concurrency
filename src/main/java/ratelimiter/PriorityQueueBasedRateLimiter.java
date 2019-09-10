package ratelimiter;

import java.util.PriorityQueue;

/**
 * This is a very basic implementation of RateLimiter. All the requests coming in can be tracked
 * with a PriorityQueue. For each request, first we remove all the requests which are outside the
 * time boundary. To facilitate easy removal of all the older requests, we have a PriorityQueue with
 * timestamp as values. The PriorityQueue will make sure only the highest timestamps are at the top.
 * We can stop polling from the queue when the difference between the current timestamp and the one
 * on the top of the queue is less than a difference configured(1 second).
 *
 * The implementation can easily work in a SingleThreaded and low volume environment.
 *
 * Can it work for WebFrameworks?
 * Some webFrameworks have a single Selector Thread(or can be
 * configure to have Single Selector Thread). As a result, we can place this rate limiter right
 * before request is handed off to the ThreadPool.
 *
 * Socket => Selector Thread => Worked Thread Pool
 *
 * In this way we can make sure we don't have to deal with concurrency issues.
 *
 * Can it work of High Volume?
 * In high volume environment, the memory footprint can become one of
 * the prohibiting factors. Secondly, larger queue size means that we will be spending more time in
 * maintaining queue constraint(removing older request timestamps).
 */
public class PriorityQueueBasedRateLimiter {

  private final int limit;
  private final PriorityQueue<Long> requests;

  public PriorityQueueBasedRateLimiter(int limit) {
    this.requests = new PriorityQueue<>();
    this.limit = limit;
  }

  public boolean allow() {
    long ts = System.currentTimeMillis();
    while (this.requests.size() > 0) {
      long top = this.requests.peek();
      if (ts - top > 1000) {
        this.requests.poll();
      } else {
        break;
      }
    }

    if (this.requests.size() < limit) {
      this.requests.add(ts);
      return true;
    }

    return false;
  }
}
