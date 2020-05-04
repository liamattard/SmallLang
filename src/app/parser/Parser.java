package app.parser;

import java.util.List;

import app.lexer.Lexer;
import app.parser.nodes.AstNode;
import app.parser.tools.ParserTools;

public class Parser {

  public static AstNode buildTree(Lexer lexer) {
    ParserTools newParser = new ParserTools(lexer);
    return newParser.parseProgram();
  }

  /**
   * Print the AST Tree.
   * 
   * @param node current Node
   */
  public static void printTree(AstNode node, boolean newline) {
    
    if (newline == true){
      System.out.println("");
    }

    System.out.print(node.toString() + " ");

    List<AstNode> children = node.getChildren();
    
    for (int i = 0; i < children.size() - 1; i++) {
      printTree(children.get(i), false);
    }

    if (children.size() != 0) {
      printTree(children.get(children.size() - 1), true);
    }
    
    
  }

}