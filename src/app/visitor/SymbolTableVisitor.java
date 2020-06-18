package app.visitor;

import app.parser.models.AstNodes.AstAdditiveOp;
import app.parser.models.AstNodes.AstAssignmentNode;
import app.parser.models.AstNodes.AstBlock;
import app.parser.models.AstNodes.AstBoolLiteral;
import app.parser.models.AstNodes.AstFloatLiteral;
import app.parser.models.AstNodes.AstForStatement;
import app.parser.models.AstNodes.AstFunctionCall;
import app.parser.models.AstNodes.AstFunctionDecl;
import app.parser.models.AstNodes.AstIfStatement;
import app.parser.models.AstNodes.AstIntLiteral;
import app.parser.models.AstNodes.AstMultiplicativeOp;
import app.parser.models.AstNodes.AstNode;
import app.parser.models.AstNodes.AstParam;
import app.parser.models.AstNodes.AstPrintNode;
import app.parser.models.AstNodes.AstProgramNode;
import app.parser.models.AstNodes.AstRelationalOp;
import app.parser.models.AstNodes.AstReturnNode;
import app.parser.models.AstNodes.AstUnary;
import app.parser.models.AstNodes.AstVariableDeclNode;
import app.parser.models.AstNodes.IdentifierNode;
import app.parser.models.Type;
import app.visitor.models.ExpressionTable;
import app.visitor.models.ExpressionType;
import app.visitor.models.FunctionIdentifier;
import app.visitor.models.Identifier;
import app.visitor.models.VariableIdentifier;

import java.util.List;

public class SymbolTableVisitor implements Visitor {

  private Visitor visitor;
  private SymbolTable symbolTable;
  private FunctionIdentifier currentFunction;
  private ExpressionTable currentExpression;
  private ExpressionType type;
  private VariableIdentifier currentVariable;

  /**
   * Checks semantics following the visitor design pattern.
   * 
   * @param rootNode root of the tree.
   * @param visitor  current visitor class;
   */
  public void checkSemantics(AstNode rootNode, Visitor visitor) {

    this.visitor = visitor;
    symbolTable = new SymbolTable();

    rootNode.accepts(visitor);

  }

  @Override
  public void visit(AstProgramNode programNode) {

    symbolTable.push();

    List<AstNode> children = programNode.getChildren();

    for (AstNode astNode : children) {
      astNode.accepts(visitor);
    }

  }

  @Override
  public void visit(AstVariableDeclNode variableDeclNode) {

    List<AstNode> children = variableDeclNode.getChildren();

    // Sets currentVaraible
    children.get(0).accepts(visitor);
    children.remove(0);

    currentExpression = new ExpressionTable();

    for (AstNode astNode : children) {
      astNode.accepts(visitor);

    }

    if (currentExpression != null) {

      type = currentExpression.getType();

    }
    
    
    ExpressionType currentType = type;
    type = null;

    Type variableType = null;

    if (currentType == ExpressionType.FLOAT) {

      variableType = Type.FLOAT;

    } else if (currentType == ExpressionType.INT) {

      variableType = Type.INTEGER;

    } else if (currentType == ExpressionType.BOOL) {

      variableType = Type.BOOL;

    } else {

      System.out.println("Error");

    }

    if (variableDeclNode.getType() != Type.AUTO) {
      
      if (variableType.toString() != variableDeclNode.getType().toString()){
        System.out.println(
                          "Type Error, Variable " + currentVariable.getName()
                          + "expected " + variableDeclNode.getType().toString()
        );
      }

          
    } 
    symbolTable.insert(currentVariable, variableType);

    currentExpression = null;

  }

  @Override
  public void visit(AstAssignmentNode assignmentNode) {

    List<AstNode> children = assignmentNode.getChildren();

    // Setting current variable
    children.get(0).accepts(visitor);
    children.remove(0);

    VariableIdentifier assignmentVariable = new VariableIdentifier();
    assignmentVariable.setName(currentVariable.getName());

    currentExpression = new ExpressionTable();
    children.get(0).accepts(visitor);
    type = currentExpression.getType();
    currentExpression = null;

    // Getting the type of the left side of the assignment
    Type identifierType = symbolTable.lookup(assignmentVariable);

    if (identifierType.toString() != type.toString()) {

      System.out.println(
                        "Error in Assignment. " + currentVariable.getName() 
                        + " recieved " + type + "Expected" + identifierType
      );

    }    

    type = null;

  }

  @Override
  public void visit(AstBlock block) {

    List<AstNode> children = block.getChildren();

    for (AstNode astNode : children) {
      astNode.accepts(visitor);
    }

  }

  @Override
  public void visit(AstIfStatement ifStatement) {

    type = null;

    List<AstNode> children = ifStatement.getChildren();

    currentExpression = new ExpressionTable();

    children.get(0).accepts(visitor);

    type = currentExpression.getType();
    currentExpression = null;

    if (type != ExpressionType.BOOL) {
      System.out.println("Error in if statement, Expected Boolean");
    }

    type = null;

    children.remove(0);

    symbolTable.push();

    children.get(0).accepts(visitor);

    symbolTable.pop();
    
  }

  @Override
  public void visit(AstAdditiveOp additiveOperator) {

    operators(additiveOperator, ExpressionType.ADDOP);
  }

  @Override
  public void visit(AstBoolLiteral boolLiteral) {
    constants(ExpressionType.BOOL);
  }

  @Override
  public void visit(AstFloatLiteral astFloatLiteral) {
    constants(ExpressionType.FLOAT);
  }

  @Override
  public void visit(AstForStatement forStatement) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(AstFunctionCall functionCall) {

    FunctionIdentifier identifier = new FunctionIdentifier();
    identifier.setName(functionCall.getFunctionName());

    List<AstNode> children = functionCall.getChildren();
  
    for (AstNode astNode: children) {

      currentExpression = new ExpressionTable();

      astNode.accepts(visitor);

      if (currentExpression  !=  null) {

        checkType(currentExpression.getType(), identifier);

      } else {
        checkType(type, identifier);
      }

        
      currentExpression = null;


    }

    Type t = symbolTable.lookup(identifier);

    if (t == Type.BOOL) { 
      type = ExpressionType.BOOL;

    }

    if (t == Type.INTEGER) { 
      type = ExpressionType.INT;

    }

    if (t == Type.FLOAT) { 
      type = ExpressionType.FLOAT;

    }

  }

  @Override
  public void visit(AstFunctionDecl functionDecl) {

    FunctionIdentifier identifier = new FunctionIdentifier();
    Type functionType = functionDecl.getType();
    boolean validity;

    identifier.setName(functionDecl.getFunctionName());
    currentFunction = identifier;

    if (functionType != Type.AUTO) {

      symbolTable.insert(identifier, functionType);

    } else {

      functionType = null;

    }

    symbolTable.push();

    List<AstNode> children = functionDecl.getChildren();

    for (AstNode astNode : children) {
      astNode.accepts(visitor);
    }

    ExpressionType currentType = type;
    type = null;

    if (functionType != null) {
      if (currentType == ExpressionType.FLOAT && functionType == Type.FLOAT) {
        validity = true;
      } else if (currentType == ExpressionType.INT && functionType == Type.INTEGER) {
        validity = true;
      } else if (currentType == ExpressionType.BOOL && functionType == Type.BOOL) {
        validity = true;
      } else {
        validity = false;

        System.out.println("Error, function returned " + currentType + " Expected + ");
      }

      symbolTable.pop();

    } else {
      if (currentType == ExpressionType.FLOAT) {

        functionType = Type.FLOAT;

      } else if (currentType == ExpressionType.INT) {

        functionType = Type.INTEGER;

      } else if (currentType == ExpressionType.BOOL) {

        functionType = Type.BOOL;

      } else {

        System.out.println("Error");

      }

      symbolTable.pop();
      symbolTable.insert(identifier, functionType);

    }


  }

  @Override
  public void visit(AstIntLiteral intLiteral) {
    constants(ExpressionType.INT);
  }

  @Override
  public void visit(AstMultiplicativeOp multiplicativeOp) {
    operators(multiplicativeOp, ExpressionType.MULOP);
  }

  @Override
  public void visit(AstParam param) {

    VariableIdentifier identifier = new VariableIdentifier();
    Type type = param.getT();
    
    if (type == Type.AUTO) {
       System.out.println("Error, Param cannot be of type Auto");
    }

    identifier.setName(param.getName());
    currentFunction.addParameter(type);
    symbolTable.insert(identifier, type);

  }

  @Override
  public void visit(AstPrintNode printNode) {
      List<AstNode> children = printNode.getChildren();

      for(AstNode astNode: children){
        astNode.accepts(visitor);
      }

  }

  @Override
  public void visit(AstRelationalOp relationalOp) {
    operators(relationalOp, ExpressionType.RELOP);
  }

  @Override
  public void visit(AstReturnNode returnNode) {
    type = null;

    List<AstNode> children = returnNode.getChildren();

    currentExpression = new ExpressionTable();

    for (AstNode astNode : children) {
      astNode.accepts(visitor);
    }

    type = currentExpression.getType();
    currentExpression = null;

  }

  @Override
  public void visit(AstUnary unary) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(IdentifierNode identifier) {

    VariableIdentifier variableIdentifier = new VariableIdentifier();
    variableIdentifier.setName(identifier.getName());

    if (currentExpression != null) {

      Type identifierType = symbolTable.lookup(variableIdentifier);

      if (identifierType == Type.FLOAT) {
        constants(ExpressionType.FLOAT);
      } else if (identifierType == Type.INTEGER) {
        constants(ExpressionType.INT);
      } else if (identifierType == Type.BOOL) {
        constants(ExpressionType.BOOL);
      } else {
        System.out.println("ERROR");
      }

    } else {

      currentVariable = variableIdentifier;

    }

  }

  private void operators(AstNode node, ExpressionType expressionType) {

    if (currentExpression.isEmpty()) {

      currentExpression.add(expressionType);

      List<AstNode> children = node.getChildren();

      for (AstNode astNode : children) {
        astNode.accepts(visitor);
      }


    } else {

      currentExpression.push();
      currentExpression.add(expressionType);

      List<AstNode> children = node.getChildren();

      for (AstNode astNode : children) {
        astNode.accepts(visitor);
      }

      type = currentExpression.getType();
      currentExpression.pop();
      currentExpression.add(type);

    }
  }

  private void constants(ExpressionType expressionType) {
    currentExpression.add(expressionType);
  }



public void checkType(ExpressionType expressionType, FunctionIdentifier identifier){

    if (expressionType == ExpressionType.INT){

      identifier.addParameter(Type.INTEGER);

    }

    if (expressionType  == ExpressionType.FLOAT){


      identifier.addParameter(Type.FLOAT);

    }

    if (expressionType == ExpressionType.BOOL){

      identifier.addParameter(Type.BOOL);

    }

  }
}