package app.parser.tools;

import app.lexer.Lexer;
import app.lexer.models.Token;
import app.lexer.models.TokenType;
import app.parser.nodes.AstNode;
import app.parser.nodes.AstProgramNode;
import app.parser.nodes.AstStatementNode;
import app.parser.nodes.AstVariableDeclNode;
import app.parser.nodes.ColonNode;
import app.parser.nodes.IdentifierNode;
import app.parser.nodes.LetNode;

import java.util.HashMap;
import java.util.Map;

public class ParserTools {
  private Lexer lexer;

  public ParserTools(Lexer lexer) {
    this.lexer = lexer;
  }

  /**
   * Returns the root of the AST Tree, ie. the ASTProgram node.
   * 
   * @return the root node
   */
  public AstNode parseProgram() {

    AstNode astProgramNode = new AstProgramNode();
    // TODO: fix this loop
    for (int i = 0; i < 1; i++) {
      astProgramNode.addItem(parseStatement());
    }
    return astProgramNode;
  }

  private AstNode parseStatement() {

    final AstNode astStatementNode = new AstStatementNode();
    Map<TokenType, AstNode> tokenWithFunction = new HashMap<TokenType, AstNode>();
    final Token lookahead = lexer.getNextToken();

    tokenWithFunction.put(TokenType.LET, parseVariableDecl());
    tokenWithFunction.put(TokenType.IDENTIFIER, parseAssignment());
    tokenWithFunction.put(TokenType.PRINT, parsePrintStatement());
    tokenWithFunction.put(TokenType.IF, parseIfStatement());
    tokenWithFunction.put(TokenType.FOR, parseForStatement());
    tokenWithFunction.put(TokenType.WHILE, parseWhileStatement());
    tokenWithFunction.put(TokenType.RETURN, parseReturnStatement());
    tokenWithFunction.put(TokenType.FF, parseFunctionDecl());
    tokenWithFunction.put(TokenType.OPENBRACKETS, parseBlock());

    astStatementNode.addItem(tokenWithFunction.get(lookahead.getTokenType()));

    return astStatementNode;
  }

  private AstNode parseVariableDecl() {
    AstNode astVaribaleDecl = new AstVariableDeclNode();
    Token lookahead = lexer.getNextToken();

    astVaribaleDecl.addItem(parseLet());

    if (lookahead.getTokenType() == TokenType.IDENTIFIER) {
      astVaribaleDecl.addItem(parseIdentifier());
      lookahead = lexer.getNextToken();

      if (lookahead.getTokenType() == TokenType.COLON) {
        astVaribaleDecl.addItem(parseColon());
        lookahead = lexer.getNextToken();

        if (lookahead.getTokenType() == TokenType.TYPE) {
          System.out.println("GG");
        } else if (lookahead.getTokenType() == TokenType.AUTO) {
          System.out.println("GG WITH AUTO");
        } else {
          System.out.println("ERROR");
        }
      }
    } else {
      System.out.println("ERROR");
    }

    return astVaribaleDecl;
  }

  // TODO: Implement this function
  private AstNode parseAssignment() {
    return null;
  }

  // TODO: Implement this function
  private AstNode parsePrintStatement() {
    return null;
  }

  // TODO: Implement this function
  private AstNode parseIfStatement() {
    return null;
  }

  // TODO: Implement this function
  private AstNode parseForStatement() {
    return null;
  }

  // TODO: Implement this function
  private AstNode parseWhileStatement() {
    return null;
  }

  // TODO: Implement this function
  private AstNode parseReturnStatement() {
    return null;
  }

  // TODO: Implement this function
  private AstNode parseFunctionDecl() {
    return null;
  }

  // TODO: Implement this function
  private AstNode parseBlock() {
    return null;
  }

  private AstNode parseLet() {
    return new LetNode();
  }

  private AstNode parseIdentifier() {
    return new IdentifierNode();
  }

  private AstNode parseColon() {
    return new ColonNode();
  }
}