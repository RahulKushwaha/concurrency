package circularqueue;

public class CircularQueueWithCounter {

  private final int elements[];
  private final int maxSize;
  private int r;
  private int w;
  private int size;

  public CircularQueueWithCounter(int size) {
    this.elements = new int[size];
    this.maxSize = size;
    this.r = 0;
    this.w = 0;
    this.size = 0;
  }

  public boolean enQueue(int value) {
    if (this.size >= this.maxSize) {
      return false;
    }

    this.elements[this.w++] = value;
    if (this.w == this.maxSize) {
      this.w = 0;
    }

    this.size++;
    return true;
  }

  public boolean deQueue() {
    if (this.size <= 0) {
      return false;
    }

    this.r++;
    if (this.r == this.maxSize) {
      this.r = 0;
    }

    this.size--;
    return true;
  }

  public int rear() {
    return this.elements[this.r];
  }

  public int front() {
    return this.elements[this.w];
  }
}
