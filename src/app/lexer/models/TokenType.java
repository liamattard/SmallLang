package app.lexer.models;

import app.lexer.tables.TokenTypeTable.State;

public enum TokenType {

  BOOLENALITERAL, INTEGERLITERAL, FLOATLITERAL, TYPE, AUTO, 
  IDENTIFIER, MULTIPLICATIVEOP, ADDITIVEOP, RELATIONALOP,
  SYMBOL, FF, WHILE, FOR, IF, ELSE, COMMENT, RETURN, PRINT, LET,
  OPENBRACKETS, CLOSEDBRACKETS, COLON, OPENPARENTHESIS, CLOSEPARENTHESIS,
  NOT, EQUALS;

  // TODO: EOF TOKEN AND ERROR TOKEN

  /**
   * Get the type of a token from the state and the lexeme.
   * 
   * @return TokenType
   */
  public static TokenType getTokenType(State state, String lexeme) {

    if (state == State.IDENTIFIER) {
      if (lexeme.equals("float") || lexeme.equals("int") || lexeme.equals("bool")) {
        return TYPE;
      } else if (lexeme.equals("auto")) {
        return AUTO;
      } else if (lexeme.equals("true") || lexeme.equals("false")) {
        return BOOLENALITERAL;
      } else if (lexeme.equals("and")) {
        return MULTIPLICATIVEOP;
      } else if (lexeme.equals("or")) {
        return ADDITIVEOP;
      } else if (lexeme.equals("let")) {
        return LET;
      } else if (lexeme.equals("print")) {
        return PRINT;
      } else if (lexeme.equals("for")) {
        return FOR;
      } else if (lexeme.equals("while")) {
        return WHILE;
      } else if (lexeme.equals("ff")) {
        return FF;
      } else if (lexeme.equals("return")) {
        return RETURN;
      } else if (lexeme.equals("not")) {
        return NOT;
      } else {
        return IDENTIFIER;
      }
    } else if (state == State.DIGIT) {
      return INTEGERLITERAL;
    } else if (state == State.EQUALS) {
      return EQUALS;
    } else if (state == State.FLOAT) {
      return FLOATLITERAL;
    } else if (state == State.COMMENT) {
      return COMMENT;
    } else if (state == State.GREATERTHAN || state == State.RELATION
          || state == State.SMALLERTHAN) {
      return RELATIONALOP;
    } else if (state == State.SLASH) {
      return MULTIPLICATIVEOP;
    } else if (state == State.SYMBOL) {
      if (lexeme.equals("{")) {
        return OPENBRACKETS;
      } else if (lexeme.equals("}")) {
        return CLOSEDBRACKETS;
      } else if (lexeme.equals(":")) {
        return COLON;
      } else if (lexeme.equals("(")) {
        return OPENPARENTHESIS;
      } else if (lexeme.equals(")")) {
        return CLOSEPARENTHESIS; 
      } else if (lexeme.equals("+") || lexeme.equals("/")) {
        return ADDITIVEOP;
      } else if (lexeme.equals("*") || lexeme.equals("-")) {
        return MULTIPLICATIVEOP;
      } else {
        return SYMBOL;
      }
    }

    return null;
  }
}