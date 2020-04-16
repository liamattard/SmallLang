package app;

import app.lexer.Lexer;
import app.lexer.filetools.FileReader;

// import java.io.*;
// import java.util.*;

public class App {
  /**
   * Main Class of the application.
   */
  public static void main(String[] args) throws Exception {

    FileReader xfile = new FileReader("float.smalllang");
    Lexer lexer = new Lexer(xfile);
    lexer.nextWord();
  }

}