package app;

import app.lexer.Lexer;
import app.lexer.filetools.SmallLangReader;


public class App {
  /**
   * Main Class of the application.
   */
  public static void main(String[] args) throws Exception {

    SmallLangReader xfile = new SmallLangReader("example.smalllang");
    Lexer lexer = new Lexer(xfile);
    
      for (int i = 0; i < 500; i++) {
        System.out.println(lexer.getNextToken().toString());
      }


  }

}