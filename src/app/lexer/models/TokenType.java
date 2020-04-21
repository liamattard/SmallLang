package app.lexer.models;

import app.lexer.tables.TokenTypeTable.State;

public enum TokenType {

  BOOLENALITERAL, INTEGERLITERAL, FLOATLITERAL, TYPE, AUTO, IDENTIFIER, MULTIPLICATIVEOP, ADDITIVEOP, RELATIONALOP,
  SYMBOL, FUNCTIONCALL, WHILE, FOR, IF, ELSE, COMMENT, RETURN, PRINT, LET;
  
  // TODO: EOF TOKEN AND ERROR TOKEN

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
        return FUNCTIONCALL;
      } else if (lexeme.equals("return")){
        return RETURN;
      } else {
        return IDENTIFIER;
      }
    } else if (state == State.DIGIT) {
      return INTEGERLITERAL;
    } else if (state == State.EQUALS) {
      return SYMBOL;
    } else if (state == State.FLOAT) {
      return FLOATLITERAL;
    } else if (state == State.COMMENT) {
      return COMMENT;
    } else if (state == State.GREATERTHAN || state == State.RELATION || state == State.SMALLERTHAN) {
      return RELATIONALOP;
    } else if (state == State.SLASH) {
      return MULTIPLICATIVEOP;
    } else if (state == State.SYMBOL) {
      return SYMBOL;
    }

    return null;
  }
}