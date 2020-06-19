package app.visitor;

import app.parser.models.AstNodes.AstAdditiveOp;
import app.parser.models.AstNodes.AstArrayDecl;
import app.parser.models.AstNodes.AstAssignmentNode;
import app.parser.models.AstNodes.AstBlock;
import app.parser.models.AstNodes.AstBoolLiteral;
import app.parser.models.AstNodes.AstCharLiteral;
import app.parser.models.AstNodes.AstFloatLiteral;
import app.parser.models.AstNodes.AstForStatement;
import app.parser.models.AstNodes.AstFunctionCall;
import app.parser.models.AstNodes.AstFunctionDecl;
import app.parser.models.AstNodes.AstIfStatement;
import app.parser.models.AstNodes.AstIntLiteral;
import app.parser.models.AstNodes.AstMultiplicativeOp;
import app.parser.models.AstNodes.AstParam;
import app.parser.models.AstNodes.AstPrintNode;
import app.parser.models.AstNodes.AstProgramNode;
import app.parser.models.AstNodes.AstRelationalOp;
import app.parser.models.AstNodes.AstReturnNode;
import app.parser.models.AstNodes.AstUnary;
import app.parser.models.AstNodes.AstVariableDeclNode;
import app.parser.models.AstNodes.IdentifierNode;

public interface Visitor {

  public void visit(AstProgramNode programNode);

  public void visit(AstVariableDeclNode variableDeclNode);

  public void visit(AstAssignmentNode assignmentNode);

  public void visit(AstBlock block);

  public void visit(AstIfStatement ifStatement);

  public void visit(AstAdditiveOp additiveOperator);

  public void visit(AstBoolLiteral boolLiteral);

  public void visit(AstFloatLiteral astFloatLiteral);

  public void visit(AstForStatement forStatement);

  public void visit(AstFunctionCall functionCall);

  public void visit(AstFunctionDecl functionDecl);

  public void visit(AstIntLiteral intLiteral);

  public void visit(AstMultiplicativeOp multiplicativeOp);

  public void visit(AstParam param);

  public void visit(AstPrintNode printNode);

  public void visit(AstRelationalOp relationalOp);

  public void visit(AstReturnNode returnNode);

  public void visit(AstUnary unary);

  public void visit(IdentifierNode identifier);

  public void visit(AstCharLiteral charLiteral);

  public void visit(AstArrayDecl arrayDecl);

}
