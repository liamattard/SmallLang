package app.lexer;

import app.lexer.filetools.SmallLangReader;
import app.lexer.models.Attributes;
import app.lexer.models.Token;
import app.lexer.models.TokenType;
import app.lexer.tables.ClassifierTable;
import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;
import app.lexer.tables.TransitionTable;

import java.util.Stack;

public class Lexer {
  
  private Token token = null;
  private String lexeme;
  private State state;
  private Stack<State> stack = new Stack<State>();

  private SmallLangReader file;

  public Lexer(SmallLangReader file) {
    this.file = file;
  }

  /**
   * Follows the Table driven lexer pseudo-code from the slide notes.
   */
  public void nextWord() {
    
    state = State.START;
    lexeme = "";
    stack.clear();
    stack.push(State.BAD);

    while (state != State.ERROR) {

      char c = file.nextChar();
      lexeme += c;

      if (state.isAcceptState()) {
        stack.clear();
      }
      stack.push(state);
      Type cat = ClassifierTable.getType(c);
      state = TransitionTable.transition(state, cat);

    }

    while (!state.isAcceptState() && state != State.BAD) {
      state = stack.pop();
      truncate();
      file.rollBack(state);
    }

    if (state.isAcceptState()) {
      file.clearRollBack(lexeme);
      setToken();
    }

  }

  private void setToken(){
    TokenType tokenType = TokenType.getTokenType(state, lexeme);
    if(tokenType != TokenType.COMMENT){
      token = new Token();
      token.setTokenType(tokenType);
      token.setAttributes(new Attributes(lexeme, file.getLineNumber(), file.getCharacterNumber()));
    }
  }

  private void truncate() {
    if (lexeme.length() > 0) {
      lexeme = lexeme.substring(0, lexeme.length() - 1);
    }
  }

  public Token getNextToken(){
    token = null;
    while(token == null) {
      nextWord();
    }
    return token;
  }
}