package app.parser.tools;

import app.lexer.Lexer;
import app.lexer.models.Token;
import app.lexer.models.TokenType;
import app.parser.models.AstNodes.*;
import app.parser.models.Type;

public class ParserTable {

  private Lexer lexer;
  private Token lookahead;

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
    lookahead = lexer.getNextToken();
    for (int i = 0; i < 1; i++) {
      
      astProgramNode.addItem(parseStatement());
      
    }

    return astProgramNode;
  }

  private AstNode parseStatement() {

    final AstNode astStatementNode;

    TokenType token = lookahead.getTokenType();

    switch (token) {

      case LET:
        astStatementNode = parseVariableDecl();
        break;

      case IDENTIFIER:
        astStatementNode = parseAssignment();
        break;
      
      case PRINT:
        astStatementNode = simpleStatement(new AstPrintNode());
        break;

      case RETURN:
        astStatementNode = simpleStatement(new AstReturnNode());
        break;

      case OPENBRACKETS:
        astStatementNode = parseBlock(0);
        break;
      
      case FF:
        astStatementNode = parseFunctionDecl();
        break;
      
      case IF:
        astStatementNode = parseIfStatement();
        break;

      default:
        astStatementNode = null;
        System.out.println("ERROR excpected Statement, recieved " + lookahead.getTokenType());
        break;
    }

    // tokenWithFunction.put(TokenType.FOR, parseForStatement());
    // tokenWithFunction.put(TokenType.WHILE, parseWhileStatement());

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
      
      if (token == TokenType.OPENSQUAREBRACKETS) {

        lookahead = lexer.getNextToken();
        token = lookahead.getTokenType();
        lexeme = lookahead.getAttributes().getLexeme();

        if (token == TokenType.INTEGERLITERAL) {

          astVaribaleDecl.setSize(Integer.parseInt(lexeme)); 

          lookahead = lexer.getNextToken();
          token = lookahead.getTokenType();
          lexeme = lookahead.getAttributes().getLexeme();


          if (token == TokenType.CLOSESQUAREBRACKETS) {

            lookahead = lexer.getNextToken();
            token = lookahead.getTokenType();
            lexeme = lookahead.getAttributes().getLexeme();
               
          } else {

            System.out.println("Missing Close brackets for array declaration");

          }

        } else {

          System.out.println("Error must be declared with size");

        } 
      }
      
      if (token == TokenType.COLON) {
        lookahead = lexer.getNextToken();
        token = lookahead.getTokenType();
        lexeme = lookahead.getAttributes().getLexeme();

        if (token == TokenType.TYPE || token == TokenType.AUTO) {

          astVaribaleDecl.setType(Type.valueOf(lexeme.toUpperCase()));
          lookahead = lexer.getNextToken();
          token = lookahead.getTokenType();

          if (token == TokenType.EQUALS) {
            lookahead = lexer.getNextToken();
            token = lookahead.getTokenType();

            AstNode expression;

            if (token == TokenType.OPENBRACKETS) {
              
              expression =  parseArrayDecl();

            } else {

              expression = parseExpression();

            }


            if (lookahead.getTokenType() == TokenType.SEMICOLON) {

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
    
    lookahead = lexer.getNextToken();

    return astVaribaleDecl;
  }

  private AstNode parseAssignment() {

    AstNode assignment = new AstAssignmentNode();
    assignment.addItem(parseIdentifier(lookahead.getAttributes().getLexeme()));
    lookahead = lexer.getNextToken();

    if (lookahead.getTokenType() == TokenType.EQUALS) {

      lookahead = lexer.getNextToken();
      AstNode expression = parseExpression();
      if (lookahead.getTokenType() == TokenType.SEMICOLON) {

        assignment.addItem(expression);

      } else {
        System.out.println("Error, Expected semicolon");
      }
    } else {
      System.out.println("Error expected Equals");
    }
    lookahead = lexer.getNextToken();
    
    return assignment;
  }

  private AstNode parseIfStatement() {
    AstNode ifStatement = new AstIfStatement();
    lookahead = lexer.getNextToken();

    if (lookahead.getTokenType() == TokenType.OPENPARENTHESIS) {
      lookahead = lexer.getNextToken();
      AstNode expression = parseExpression();
      ifStatement.addItem(expression);

      if (lookahead.getTokenType() == TokenType.CLOSEPARENTHESIS) {
        lookahead = lexer.getNextToken();
        AstNode trueBlock = parseBlock(1);
       
        ifStatement.addItem(trueBlock);
        
        // lookahead = lexer.getNextToken();
        
        if (lookahead.getTokenType() == TokenType.ELSE) {
          lookahead = lexer.getNextToken();
          AstNode falseBlock = parseBlock(2);
          ifStatement.addItem(falseBlock);
          // lookahead = lexer.getNextToken();
        } 
        // System.out.println(lookahead.getAttributes().getLexeme());
        

      } else {
        System.out.println("Error expected closed paranthesis in if statement");
      }

    } else {
      System.out.println("Expected parathesis for if statment");
    }
    return ifStatement;
  }

  private AstNode simpleStatement(AstNode node) {
    lookahead = lexer.getNextToken();
    AstNode expression = parseExpression();
    

    if (lookahead.getTokenType() == TokenType.SEMICOLON) {
      node.addItem(expression);
    } else {
      System.out.println("Error, Expected Semicolon after printstatement");
    }
    
    lookahead = lexer.getNextToken();
    return node;
  }
  
  private AstNode parseBlock(int validity) {
    AstBlock block = new AstBlock();
    block.setValidity(validity);
    lookahead = lexer.getNextToken();

    while (lookahead.getTokenType() != TokenType.CLOSEDBRACKETS) {
      
      block.addItem(parseStatement());
      
      // System.out.println(lookahead.getAttributes().getLexeme());
    }
    
    lookahead = lexer.getNextToken();
    return block;
  }

  private AstNode parseFunctionDecl() {
    
    AstFunctionDecl function = new AstFunctionDecl();
    lookahead = lexer.getNextToken();
    
    if (lookahead.getTokenType() == TokenType.IDENTIFIER) {
      
      function.setFunctionName(lookahead.getAttributes().getLexeme());
      lookahead = lexer.getNextToken();

      if (lookahead.getTokenType() == TokenType.OPENPARENTHESIS) {
        boolean error = true;

        while (error == true) {
          lookahead = lexer.getNextToken();
          AstNode param = parseFormalParam();
          function.addItem(param);
          lookahead = lexer.getNextToken();

          if (lookahead.getTokenType() != TokenType.COMMA) {
            if (lookahead.getTokenType() != TokenType.CLOSEPARENTHESIS) {
              System.out.println("ERROR EXPECTED COMMA");
              error = false;
            } else {
              error = false;
            }
          }
          
          
        }
        
        lookahead = lexer.getNextToken();

        if (lookahead.getTokenType() == TokenType.COLON) {

          lookahead = lexer.getNextToken();
          
          if (lookahead.getTokenType() == TokenType.TYPE
              || lookahead.getTokenType() == TokenType.AUTO) {
            String lexeme = lookahead.getAttributes().getLexeme();

            function.setType(Type.valueOf(lexeme.toUpperCase()));

            lookahead = lexer.getNextToken();
            function.addItem(parseBlock(0));
          } else {
            System.out.println("Expected Function Type or auto");
          }
          
        } else {
          System.out.println("Expected colon for function");
        }

      } else {
        System.out.println("Error, Expected Open Brackets for start of program");
      }
    } else {
      System.out.println("Error, Expected function name");
    }
    // lookahead = lexer.getNextToken();
    return function;
  }
  
  private AstNode parseFormalParam() {

    AstParam param = new AstParam();
    param.setName(lookahead.getAttributes().getLexeme());
    lookahead = lexer.getNextToken();

    if (lookahead.getTokenType() == TokenType.OPENSQUAREBRACKETS) {

      lookahead = lexer.getNextToken();
      
      if (lookahead.getTokenType() == TokenType.CLOSESQUAREBRACKETS) {

        param.setIsArray(true);

      } else {
        System.out.println("Expected Close Brackets");
      }
      
      lookahead = lexer.getNextToken();

    }


    if (lookahead.getTokenType() == TokenType.COLON) {

      lookahead = lexer.getNextToken();
      
      if (lookahead.getTokenType() == TokenType.TYPE) {
        String lexeme = lookahead.getAttributes().getLexeme();
        param.setT(Type.valueOf(lexeme.toUpperCase()));
      }

    } else {

      System.out.println("Error, Expected colon for formalParam");

    }

    return param;
  }

  private AstNode parseArrayDecl() {

    AstNode arrayDecl = new AstVariableDeclNode();

    lookahead = lexer.getNextToken();

    while (lookahead.getTokenType() != TokenType.CLOSEDBRACKETS) {

      arrayDecl.addItem(parseExpression());

      if (lookahead.getTokenType() != TokenType.COMMA 
          && lookahead.getTokenType() != TokenType.CLOSEDBRACKETS) {

        System.out.println("Expected comma in array Declaration");

      } 

      if (lookahead.getTokenType() != TokenType.CLOSEDBRACKETS ){

        lookahead = lexer.getNextToken();

      }

    }

    lookahead = lexer.getNextToken();

    return arrayDecl;

  }

  private AstNode parseExpression() {

    AstNode expression = null;
    AstNode simpleExpression = parseSimpleExpression();

    if (lookahead.getTokenType() != TokenType.RELATIONALOP) {

      expression = simpleExpression;

    } else {

      do {
        expression = new AstRelationalOp(lookahead.getAttributes().getLexeme());
        expression.addItem(simpleExpression);
        lookahead = lexer.getNextToken();
        expression.addItem(parseExpression());
      } while (lookahead.getTokenType() == TokenType.RELATIONALOP);

    }

    return expression;
  }

  private AstNode parseSimpleExpression() {
    AstNode simpleExpression = null;
    AstNode tempNode;

    tempNode = parseTerm();

    if (lookahead.getTokenType() != TokenType.ADDITIVEOP) {

      simpleExpression = tempNode;

    } else {

      do {

        simpleExpression = new AstAdditiveOp(lookahead.getAttributes().getLexeme());
        simpleExpression.addItem(tempNode);
        lookahead = lexer.getNextToken();
        simpleExpression.addItem(parseSimpleExpression());

      } while (lookahead.getTokenType() == TokenType.ADDITIVEOP);

    }

    return simpleExpression;
  }

  private AstNode parseTerm() {

    AstNode term = null;

    AstNode tempNode = parseFactor();

    if (lookahead.getTokenType() != TokenType.MULTIPLICATIVEOP) {

      term = tempNode;

    } else {

      do {
        term = new AstMultiplicativeOp(lookahead.getAttributes().getLexeme());
        term.addItem(tempNode);
        lookahead = lexer.getNextToken();
        term.addItem(parseTerm());
      } while (lookahead.getTokenType() == TokenType.MULTIPLICATIVEOP);

    }

    return term;
  }

  /*
   * This function parser the EBNF Factor Non Terminal symbol which checks whether
   * the next token contains a literal or an identifier or a functioncall/
   * subexpression or unary.
   */
  private AstNode parseFactor() {

    AstNode factorNode = null;
    TokenType token = lookahead.getTokenType();
    String lexeme = lookahead.getAttributes().getLexeme();

    lookahead = lexer.getNextToken();
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
        factorNode = identifierOrFunctionCall(lexeme);
        break;

      case OPENPARENTHESIS:
        factorNode = parseSubExpression();
        break;

      case NOT:
        factorNode = parseUnary();
        break;

      default:
        System.out.println("ERROR in Factor");
        break;
    }

    return factorNode;
  }

  private AstNode parseUnary() {
    AstNode unary = new AstUnary();
    unary.addItem(parseExpression());

    return unary;
  }

  private AstNode parseSubExpression() {

    AstNode subexpression = parseExpression();

    if (lookahead.getTokenType() == TokenType.CLOSEPARENTHESIS) {
      lookahead = lexer.getNextToken();
      return subexpression;

    } else {
      System.out.println("Error, expected closed brackets for subexpression");
      return null;
    }
  }

  private AstNode parseIdentifier(String name) {
    return new IdentifierNode(name);
  }

  private AstNode parseFunctionCall(String name) {

    AstFunctionCall functionCall = new AstFunctionCall();
    functionCall.setFunctionName(name);
    lookahead = lexer.getNextToken();

    boolean error = false;

    while (error != true) {
      
      functionCall.addItem(parseExpression());
      
      if (lookahead.getTokenType() != TokenType.COMMA) {
        if (lookahead.getTokenType() != TokenType.CLOSEPARENTHESIS) {
          System.out.println("Error, excpected COMMA");
          error = true;
        } else {

          error = true;

        }
      }
      lookahead = lexer.getNextToken();
    }
    return functionCall;
  }

  private AstNode identifierOrFunctionCall(String name) {

    if (lookahead.getTokenType() == TokenType.OPENPARENTHESIS) {

      return parseFunctionCall(name);

    } else {

      return parseIdentifier(name);

    }
  }

}
