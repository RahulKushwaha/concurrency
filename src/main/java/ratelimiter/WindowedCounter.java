package ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

public class WindowedCounter {

  private int[] windows = new int[100];
  private AtomicLong[] timestamps = new AtomicLong[100];

  public WindowedCounter() {
    for (int i = 0; i < 100; i++) {
      this.timestamps[i] = new AtomicLong(0L);
      this.windows[i] = 0;
    }
  }

  void increment() {
    long timestamp = System.currentTimeMillis();
    int windowIndex = (int) (timestamp % 100);
    long oldTimestamp = timestamps[windowIndex].get();

    if (timestamp - oldTimestamp > 1000) {
      timestamps[windowIndex].compareAndSet(oldTimestamp, timestamp);
    }

    windows[windowIndex]++;
  }

  int getCount() {
    long timestamp = System.currentTimeMillis();
    int counter = 0;
    for (int i = 0; i < 100; i++) {
      if (timestamp - timestamps[i].get() < 1000) {
        counter += windows[i];
      }
    }

    return counter;
  }
}
