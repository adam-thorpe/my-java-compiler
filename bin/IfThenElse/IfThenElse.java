public class IfThenElse {

  public boolean ifTrue() {
    boolean input = true;

    if(input) {
      return true;
    }

    return false;
  }

  public String ifElseString(boolean input) {
    if(input) {
      return "Input was true";
    } else {
      return "Input was false";
    }
  }

  public String ifElseInt(int input) {
    int integer = 5;
    int result = input + integer;

    if(result > 10) {
      return "Input was greater than 5";
    } else if (result == 10) {
      return "Input was 5";
    } else {
      return "Input was less than 5";
    }
  }
}