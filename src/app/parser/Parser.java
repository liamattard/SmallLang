package app.parser;

import app.lexer.Lexer;

import app.parser.models.AstNodes.AstNode;
import app.parser.tools.ParserTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

  public static Map<Integer, List<String>> printMap = new HashMap<Integer, List<String>>();

  public static AstNode buildTree(Lexer lexer, String name) {
    ParserTable newParser = new ParserTable(lexer);
    return newParser.parseProgram(name);
  }

  /**
   * Print the AST Tree.
   * 
   * @param root current Node
   */
  public static void printTree(AstNode root) {
    traverseTree(root, 1);
    List<String> firstRow = new ArrayList<String>();
    firstRow.add(root.toString());
    printMap.put(0, firstRow);

    for (int i = 0; i < printMap.size(); i++) {

      List<String> row = printMap.get(i);

      for (int j = 0; j < row.size(); j++) {
        System.out.print(row.get(j));
      }
      System.out.println(" ");
    }
  }

  private static void traverseTree(AstNode n, int rowNum) {

    if (printMap.containsKey(rowNum)) {

      printMap.get(rowNum).add(n.toString());

    } else {

      List<String> newline = new ArrayList<String>();
      printMap.put(rowNum, newline);

    }

    if (n.getChildren() == null) {

      return;
    }

    for (AstNode m : n.getChildren()) {

      printMap.get(rowNum).add(m.toString());
      traverseTree(m, rowNum + 1);

    }

  }

}