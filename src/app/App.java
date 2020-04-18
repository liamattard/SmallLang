package app;

import app.lexer.Lexer;
import app.lexer.filetools.SmallLangReader;

// import java.io.*;
// import java.util.*;

public class App {
  /**
   * Main Class of the application.
   */
  public static void main(String[] args) throws Exception {

    SmallLangReader xfile = new SmallLangReader("example.smalllang");
    Lexer lexer = new Lexer(xfile);
    
    for (int i = 0; i < 500; i++) {
      lexer.nextWord();
    }


  }

}