package app.lexer;

import app.lexer.filetools.SmallLangReader;
import app.lexer.tables.ClassifierTable;
import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;
import app.lexer.tables.TransitionTable;

import java.util.Stack;

public class Lexer {

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
      file.rollBack();
    }

    if (state.isAcceptState()) {
      System.out.print("Lexeme = ");
      System.out.print(lexeme);
      System.out.print(" State = ");
      System.out.println(state.toString());
    }

  }
  
  private void truncate() {
    if (lexeme.length() > 0) {
      lexeme = lexeme.substring(0, lexeme.length() - 1);
    }
  }

}