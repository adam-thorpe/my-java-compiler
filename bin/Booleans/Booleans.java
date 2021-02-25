public class Booleans {

  public boolean True() {
    boolean output = true;
    return output;
  }

  public boolean False() {
    return false;
  }

  public boolean BoolLoop(boolean b) {
    return BoolLoop(b);
  }

  public boolean DoNotEvaluateRight() {
    return True() || BoolLoop(false);
  }

  public boolean DoNotEvaluateRight2() {
    return False() && BoolLoop(true);
  }

  public boolean EvaluateBoth() {
    return !True() || !False();
  }

  public boolean EvaluateBoth2() {
    return True() && !True();
  }

  public boolean EvaluateNested() {
    return !True() || True() && !False();
  }
}
