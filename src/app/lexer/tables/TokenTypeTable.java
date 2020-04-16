package app.lexer.tables;

public class TokenTypeTable {
  public enum State {
  
    START(false),
    DIGIT(true),
    IDENTIFIER(true),
    SYMBOL(true),
    DIGITDOT(false),
    FLOAT(true),
    ERROR(false),
    BAD(false);
  
    final boolean acceptState;
  
    private State(boolean acceptState) {
      this.acceptState = acceptState;
    }
  
    public boolean isAcceptState() {
      return acceptState;
    }
  
  }
  
}