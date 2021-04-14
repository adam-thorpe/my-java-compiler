public class PrintNumber {
  public static void main(String[] args) {
    printNumber(-5);
    printNumber(0);
    printNumber(20);
  }

  public static void printNumber(int input) {
    if (input>0) {
      System.out.println("Number is positive");
    } else if (input==0) {
      System.out.println("Number is 0");
    } else {
      System.out.println("Number is negative");
    }
  }
}