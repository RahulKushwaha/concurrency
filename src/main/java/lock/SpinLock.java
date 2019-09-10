package lock;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpinLock implements Lock {

  private AtomicBoolean flag;

  public SpinLock() {
    this.flag = new AtomicBoolean(false);
  }

  @Override
  public void lock() {
    while (this.flag.compareAndSet(false, true)) {
    }
  }

  @Override
  public void release() {
    this.flag.compareAndSet(true, false);
  }
}
