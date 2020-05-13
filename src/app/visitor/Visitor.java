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
import app.parser.models.AstNodes.AstParam;
import app.parser.models.AstNodes.AstPrintNode;
import app.parser.models.AstNodes.AstProgramNode;
import app.parser.models.AstNodes.AstRelationalOp;
import app.parser.models.AstNodes.AstReturnNode;
import app.parser.models.AstNodes.AstUnary;
import app.parser.models.AstNodes.AstVariableDeclNode;
import app.parser.models.AstNodes.IdentifierNode;

import org.w3c.dom.Element;

public interface Visitor {

  public Element visit(AstProgramNode programNode);

  public Element visit(AstVariableDeclNode variableDeclNode);

  public Element visit(AstAssignmentNode assignmentNode);

  public Element visit(AstBlock block);

  public Element visit(AstIfStatement ifStatement);

  public Element visit(AstAdditiveOp additiveOperator);

  public Element visit(AstBoolLiteral boolLiteral);

  public Element visit(AstFloatLiteral astFloatLiteral);

  public Element visit(AstForStatement forStatement);

  public Element visit(AstFunctionCall functionCall);

  public Element visit(AstFunctionDecl functionDecl);

  public Element visit(AstIntLiteral intLiteral);

  public Element visit(AstMultiplicativeOp multiplicativeOp);

  public Element visit(AstParam param);

  public Element visit(AstPrintNode printNode);

  public Element visit(AstRelationalOp relationalOp);

  public Element visit(AstReturnNode returnNode);

  public Element visit(AstUnary unary);

  public Element visit(IdentifierNode identifier);
}