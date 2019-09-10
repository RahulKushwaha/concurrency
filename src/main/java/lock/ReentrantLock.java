package lock;

import java.util.concurrent.atomic.AtomicLong;

public class ReentrantLock implements Lock {

  AtomicLong threadId;

  public ReentrantLock() {
    this.threadId = new AtomicLong();
  }

  @Override
  public void lock() {
    while (this.threadId.compareAndSet(-1, Thread.currentThread().getId())) {
    }
  }

  @Override
  public void release() {
    this.threadId.compareAndSet(Thread.currentThread().getId(), -1);
  }
}
