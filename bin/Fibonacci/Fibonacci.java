public class Fibonacci {
  public static void main(String[] args) {
    int loop=0;
    int i=1;
    int j=1;

    System.out.println(i);
    System.out.println(j);

    while(loop<8) {
      j = i + j;
      i = j - i;

      System.out.println(j);
      loop = loop + 1;
    }
  }
}