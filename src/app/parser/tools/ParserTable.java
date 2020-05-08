package app.parser.tools;

import app.lexer.Lexer;
import app.lexer.models.Token;
import app.lexer.models.TokenType;
import app.parser.models.AstNodes.*;
import app.parser.models.Type;
import java.util.HashMap;
import java.util.Map;

public class ParserTable {

  private Lexer lexer;
  private Token lookahead;
  private Token exception;

  /**
   * Parser Tool's constructor which sets the current lexer.
   * 
   * @param lexer current lexer in use.
   */
  public ParserTable(Lexer lexer) {
    this.lexer = lexer;
  }

  /**
   * Returns the root of the AST Tree, ie. the ASTProgram node.
   * 
   * @return the root node
   */
  public AstNode parseProgram(String name) {

    AstNode astProgramNode = new AstProgramNode(name);

    // TODO: fix this loop
    for (int i = 0; i < 1; i++) {
      astProgramNode.addItem(parseStatement());
    }

    return astProgramNode;
  }

  private AstNode parseStatement() {

    final AstNode astStatementNode;
    lookahead = lexer.getNextToken();
    TokenType token = lookahead.getTokenType();

    switch (token) {

      case LET:
        astStatementNode = parseVariableDecl();
        break;

      default:
        astStatementNode = null;
        System.out.println("ERROR excpected Statement, recieved " + lookahead.getTokenType());
        break;
    }

    // tokenWithFunction.put(TokenType.IDENTIFIER, parseAssignment());
    // tokenWithFunction.put(TokenType.PRINT, parsePrintStatement());
    // tokenWithFunction.put(TokenType.IF, parseIfStatement());
    // tokenWithFunction.put(TokenType.FOR, parseForStatement());
    // tokenWithFunction.put(TokenType.WHILE, parseWhileStatement());
    // tokenWithFunction.put(TokenType.RETURN, parseReturnStatement());
    // tokenWithFunction.put(TokenType.FF, parseFunctionDecl());
    // tokenWithFunction.put(TokenType.OPENBRACKETS, parseBlock());

    return astStatementNode;
  }

  private AstNode parseVariableDecl() {

    lookahead = lexer.getNextToken();
    AstVariableDeclNode astVaribaleDecl = new AstVariableDeclNode();
    TokenType token = lookahead.getTokenType();
    String lexeme = lookahead.getAttributes().getLexeme();

    if (token == TokenType.IDENTIFIER) {

      astVaribaleDecl.addItem(parseIdentifier(lexeme));
      lookahead = lexer.getNextToken();
      token = lookahead.getTokenType();

      if (token == TokenType.COLON) {
        lookahead = lexer.getNextToken();
        token = lookahead.getTokenType();
        lexeme = lookahead.getAttributes().getLexeme();

        if (token == TokenType.TYPE || token == TokenType.AUTO) {

          astVaribaleDecl.setType(Type.valueOf(lexeme.toUpperCase()));
          lookahead = lexer.getNextToken();
          token = lookahead.getTokenType();

          if (token == TokenType.EQUALS) {

            AstNode expression = parseExpression();

            if (exception.getTokenType() == TokenType.SYMBOL) {

              astVaribaleDecl.addItem(expression);

            } else {
              System.out.println("ERROR, excpected semicolon");
            }

          } else {

            System.out.println("Error, no equals found");

          }

        } else {

          System.out.println("ERROR, no type or auto found");

        }
      } else {
        System.out.println("ERROR, no colon found");
      }
    } else {
      System.out.println("ERROR, no identifier found");
    }

    return astVaribaleDecl;
  }

  private AstNode parseExpression() {

    AstNode expression = null;
    AstNode simpleExpression = parseSimpleExpression();

    if (exception.getTokenType() != TokenType.RELATIONALOP) {

      expression = simpleExpression;

    } else {

      do {
        expression = new AstRelationalOp(exception.getAttributes().getLexeme());
        expression.addItem(simpleExpression);
        expression.addItem(parseSimpleExpression());
      } while (exception.getTokenType() == TokenType.RELATIONALOP);

    }

    return expression;
  }

  private AstNode parseSimpleExpression() {
    AstNode simpleExpression = null;
    AstNode tempNode;

    tempNode = parseTerm();

    if (exception.getTokenType() != TokenType.ADDITIVEOP) {

      simpleExpression = tempNode;

    } else {

      do {

        simpleExpression = new AstAdditiveOp(exception.getAttributes().getLexeme());
        simpleExpression.addItem(tempNode);
        simpleExpression.addItem(parseSimpleExpression());

      } while (exception.getTokenType() == TokenType.ADDITIVEOP);

    }

    return simpleExpression;
  }

  private AstNode parseTerm() {

    AstNode term = null;

    AstNode tempNode = parseFactor();

    if (exception.getTokenType() != TokenType.MULTIPLICATIVEOP) {

      term = tempNode;

    } else {

      do {
        term = new AstMultiplicativeOp(exception.getAttributes().getLexeme());
        term.addItem(tempNode);
        term.addItem(parseTerm());
      } while (exception.getTokenType() == TokenType.MULTIPLICATIVEOP);

    }

    return term;
  }

  /*
   * This function parser the EBNF Factor Non Terminal symbol which checks whether
   * the next token contains a literal or an identifier or a functioncall/
   * subexpression or unary.
   */
  private AstNode parseFactor() {

    lookahead = lexer.getNextToken();
    exception = lexer.getNextToken();
    AstNode factorNode = null;
    TokenType token = lookahead.getTokenType();
    String lexeme = lookahead.getAttributes().getLexeme();

    switch (token) {

      case BOOLENALITERAL:
        factorNode = new AstBoolLiteral(lexeme);
        break;

      case INTEGERLITERAL:
        factorNode = new AstIntLiteral(lexeme);
        break;

      case FLOATLITERAL:
        factorNode = new AstFloatLiteral(lexeme);
        break;

      case IDENTIFIER:
        factorNode = identifierOrFunctionCall();
        break;

      default:
        System.out.println("ERROR");
        break;
    }

    return factorNode;
  }

  private AstNode parseIdentifier(String name) {
    return new IdentifierNode(name);
  }

  private AstNode identifierOrFunctionCall() {

    String name = lookahead.getAttributes().getLexeme();

    if (exception.getTokenType() == TokenType.OPENPARENTHESIS) {
      // Parse Functioncall
      return null;
    } else {
      return parseIdentifier(name);
    }
  }

}