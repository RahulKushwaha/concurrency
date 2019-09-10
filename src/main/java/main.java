public class main {

  public static void main(String[] args) {
    int a = 4;
    int b = Integer.MAX_VALUE;

    System.out.println(Integer.compareUnsigned(a, b));
    System.out.println(Integer.compareUnsigned(b, a));
  }
}
