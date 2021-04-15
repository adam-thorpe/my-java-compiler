public class Recursion {
  public static void main(String[] args) {
    System.out.println(divide(25, 5));
    System.out.println(divide(11, 3));
    System.out.println(divide(5, 7));
  }

  public static int divide(int number, int div) {
    if(number < div) {
      return 0;
    }

    return 1 + divide(number-div, div);
  }
}