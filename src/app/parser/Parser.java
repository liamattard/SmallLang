package app.parser;

import app.lexer.Lexer;

import app.parser.models.AstNodes.AstNode;
import app.parser.tools.ParserTable;

public class Parser {
  
  public static AstNode buildTree(Lexer lexer, String name) {
    ParserTable newParser = new ParserTable(lexer);
    return newParser.parseProgram(name);
  }

  /**
   * Print the AST Tree.
   * 
   * @param n current Node
   */
  public static void printTree(AstNode n, int rowNum) {

    System.out.println(getDashes(rowNum) + n.toString());

    for (AstNode m : n.getChildren()) {
      printTree(m, rowNum + 1);
    }

  }

  private static String getDashes(int i) {

    String dashes = "";
    for (int j = 0; j < i * 4; j++) {
      dashes += "-";
    }

    return dashes;
  }
}