package app;

import app.lexer.Lexer;
import app.lexer.filetools.SmallLangReader;
import app.parser.Parser;
import app.parser.models.AstNodes.AstNode;


public class App {

  /**
   * Main Class of the application.
   */
  public static void main(String[] args) throws Exception {

    String filename = "varibaleDeclExample.smalllang";
    SmallLangReader xfile = new SmallLangReader(filename);
    Lexer lexer = new Lexer(xfile);
    
    // for (int i = 0; i < 500; i++) {
    //   System.out.println(lexer.getNextToken().toString());
    // }

    AstNode tree = Parser.buildTree(lexer, filename);

    Parser.printTree(tree);
    
 
  }

}