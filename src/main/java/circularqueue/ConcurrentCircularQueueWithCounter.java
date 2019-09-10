package circularqueue;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCircularQueueWithCounter {

  private final int elements[];
  private final int maxSize;
  private final int cmpAndSwapAttempts;
  private int r;
  private int w;
  private AtomicInteger size;

  public ConcurrentCircularQueueWithCounter(int size) {
    this.elements = new int[size];
    this.maxSize = size;
    this.cmpAndSwapAttempts = 8;
    this.r = 0;
    this.w = 0;
    this.size = new AtomicInteger(0);
  }

  public boolean enQueue(int value) {
    int counter = 0;
    int currentSize;
    boolean cmpAndSwapSuccess = false;
    while (counter < this.cmpAndSwapAttempts) {
      counter++;
      currentSize = this.size.get();
      if (currentSize < this.maxSize) {
        cmpAndSwapSuccess = this.size.compareAndSet(currentSize, currentSize + 1);
        if (cmpAndSwapSuccess) {
          break;
        }
      }
    }

    if (!cmpAndSwapSuccess) {
      return false;
    }

    this.elements[this.w++] = value;

    if (this.w == this.maxSize) {
      this.w = 0;
    }

    return true;
  }

  public boolean deQueue() {
    int counter = 0;
    int currentSize;
    boolean cmpAndSwapSuccess = false;
    while (counter < this.cmpAndSwapAttempts) {
      counter++;
      currentSize = this.size.get();
      if (currentSize != 0) {
        cmpAndSwapSuccess = this.size.compareAndSet(currentSize, currentSize - 1);
        if (cmpAndSwapSuccess) {
          break;
        }
      }
    }

    if (!cmpAndSwapSuccess) {
      return false;
    }

    this.r++;
    if (this.r == this.maxSize) {
      this.r = 0;
    }

    return true;
  }

  public int rear() {
    return this.elements[this.r];
  }

  public int front() {
    return this.elements[this.w];
  }
}
