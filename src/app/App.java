package app;

import app.lexer.Lexer;
import app.lexer.filetools.SmallLangReader;
import app.parser.Parser;
import app.parser.models.AstNodes.AstNode;
import app.visitor.SymbolTableVisitor;
import app.visitor.XmlVisitor;


public class App {

  /**
   * Main Class of the application.
   */
  public static void main(String[] args) throws Exception {
    
    String filename = "example.smalllang";
    SmallLangReader xfile = new SmallLangReader(filename);
    Lexer lexer = new Lexer(xfile);
    
    for (int i = 0; i < 500; i++) {
      System.out.println(lexer.getNextToken().toString());
    }

    // AstNode tree = Parser.buildTree(lexer, filename);
    // Parser.printTree(tree, 0);

    // XmlVisitor visitor = new XmlVisitor();
    
    // visitor.buildXml(tree, visitor);

    // SymbolTableVisitor symbolTable = new SymbolTableVisitor();
    // symbolTable.checkSemantics(tree, symbolTable);
  }

}
