package app.lexer.tables;

public class ClassifierTable {

  public enum Type {
    DIGIT,
    DOT,
    OTHER;
  }

  /**
   * aa.
   * @param c asd
   * @return asd
   */
  public static Type getType(char c) {
    if (Character.isDigit(c)) {
      return Type.DIGIT;
    }
    if (Character.isLetter(c)) {
      return Type.OTHER;
    }
    if (c == '.') {
      return Type.DOT;
    }
    if (c == ' ') {
      return Type.OTHER;
    }
    return null;
  }

}