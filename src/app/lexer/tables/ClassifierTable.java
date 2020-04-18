package app.lexer.tables;


public class ClassifierTable {

  public enum Type {
    
    DIGIT,
    LETTER,
    UNDERSCORE,
    DOT,
    WHITESPACE,
    NEWLINE,
    SYMBOL,
    SLASH,
    GREATERTHAN,
    SMALLERTHAN,
    EQUALS,
    MULTIPLICATIVEOP,
    ADDITIVEOP,
    OTHER;
  }

  /**
   * Returns the token type chosen from the Type class.
   * 
   * @param c character to check it's type
   * @return Type based on character
   */
  public static Type getType(char c) {

    if (Character.isDigit(c)) {
      return Type.DIGIT;
    } else if (Character.isLetter(c)) {
      return Type.LETTER;
    } else if (c == '_') {
      return Type.UNDERSCORE;
    } else if (c == '.') {
      return Type.DOT;
    } else if (c == ' ') {
      return Type.WHITESPACE;
    } else if (c == '\n') {
      return Type.NEWLINE;
    } else if (c == '(' || c == ')' || c == '{' || c == '}' || c == ';'
        || c == ':' || c == ',') {
      return Type.SYMBOL;
    } else if (c == '/') {
      return Type.SLASH;
    } else if (c == '*' || c == '∗') {
      return Type.MULTIPLICATIVEOP;
    } else if (c == '+' || c == '-') {
      return Type.ADDITIVEOP;
    } else if (c == '=') {
      return Type.EQUALS;
    } else if (c == '>') {
      return Type.GREATERTHAN;
    } else if (c == '<') {
      return Type.SMALLERTHAN;
    }
    return Type.OTHER;
  }

}