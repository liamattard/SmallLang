package app.parser.models;

import java.util.ArrayList;
import java.util.List;

public class AstNodes {

  public abstract static class AstNode {

    private List<AstNode> children = new ArrayList<AstNode>();

    public void addItem(AstNode child) {
      children.add(child);
    }

    public List<AstNode> getChildren() {
      return this.children;
    }

    @Override
    public abstract String toString();
  }

  public static class AstProgramNode extends AstNode {

    private String name;

    public AstProgramNode(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return "AstProgramNode [name=" + name + "]";
    }

  }

  public static class AstAssignmentNode extends AstNode {

    @Override
    public String toString() {
      return "AstAssignmentNode";
    }

  }

  public static class AstPrintNode extends AstNode {

    @Override
    public String toString() {
      return "AstPrintStatement";
    }

  }

  public static class AstBlock extends AstNode {
    int validity = 0;

    public int getValidity() {
      return validity;
    }

    public void setValidity(int validity) {
      this.validity = validity;
    }

    @Override
    public String toString() {
      if (validity == 0) {
        return "AstBlock";
      } else {
        return "AstBlock [validity=" + validity + "]";
      }

    }

  }

  public static class AstIfStatement extends AstNode {

    @Override
    public String toString() {
      return "AstIfStatement []";
    }

  }

  public static class AstForStatement extends AstNode {

    @Override
    public String toString() {
      return "AstForStatement []";
    }
    
  }
  
  public static class AstReturnNode extends AstNode {

    @Override
    public String toString() {
      return "AstReturnNode []";
    }

  }

  public static class AstVariableDeclNode extends AstNode {

    Type type;

    @Override
    public String toString() {
      return "AstVariableDeclNode [type=" + type + "]";
    }

    public void setType(Type type) {
      this.type = type;
    }

  }

  public static class IdentifierNode extends AstNode {

    String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "IdentifierNode [name=" + name + "]";
    }

    public IdentifierNode(String name) {
      this.name = name;
    }

  }

  public abstract static class Literal extends AstNode {
    private String literal;

    public Literal(String literal) {
      this.literal = literal;
    }

    public String getLiteral() {
      return literal;
    }

  }

  public static class AstIntLiteral extends Literal {

    public AstIntLiteral(String literal) {
      super(literal);
    }

    @Override
    public String toString() {
      return "AstIntLiteral [ Literal " + getLiteral() + "]";
    }

  }

  public static class AstFloatLiteral extends Literal {

    public AstFloatLiteral(String literal) {
      super(literal);
    }

    @Override
    public String toString() {
      return "AstFloatLiteral [ Literal " + getLiteral() + "]";
    }

  }

  public static class AstBoolLiteral extends Literal {

    public AstBoolLiteral(String literal) {
      super(literal);
    }

    @Override
    public String toString() {
      return "AstBoolLiteral [ Literal " + getLiteral() + "]";
    }

  }

  public abstract static class Operator extends AstNode {
    private OperatorTypes operator;

    public Operator(OperatorTypes operator) {
      this.operator = operator;
    }

    public void setOperator(OperatorTypes operator) {
      this.operator = operator;
    }

    public OperatorTypes getOperator() {
      return operator;
    }

  }

  public static class AstMultiplicativeOp extends Operator {

    public AstMultiplicativeOp(String operator) {
      super(OperatorTypes.getOperator(operator));
    }

    @Override
    public String toString() {
      return "ASTMultiplicativeOp [ Type : " + getOperator() + " ]";
    }

  }

  public static class AstRelationalOp extends Operator {

    public AstRelationalOp(String operator) {
      super(OperatorTypes.getOperator(operator));
    }

    @Override
    public String toString() {
      return "ASTRelationalOp [ Operator :" + getOperator() + "]";
    }

  }

  public static class AstAdditiveOp extends Operator {

    public AstAdditiveOp(String operator) {
      super(OperatorTypes.getOperator(operator));
    }

    @Override
    public String toString() {
      return "ASTAdditiveOp [ operator " + getOperator() + "]";
    }

  }

  public static class AstFunctionDecl extends AstNode {

    private String functionName;
    private Type type;

    public String getFunctionName() {
      return functionName;
    }

    public void setFunctionName(String functionName) {
      this.functionName = functionName;
    }

    @Override
    public String toString() {
      return "AstFunctionDecl [functionName=" + functionName + ", type=" + type + "]";
    }

    public Type getType() {
      return type;
    }

    public void setType(Type type) {
      this.type = type;
    }

  }

  public static class AstParam extends AstNode {
    Type type;
    String name;

    public Type getT() {
      return type;
    }

    public void setT(Type type) {
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "AstParam [name=" + name + ", type=" + type + "]";
    }

  }

  public static class AstFunctionCall extends AstNode {

    private String functionName;

    @Override
    public String toString() {
      return "AstFunctionCall [functionName=" + getFunctionName() + "]";
    }

    public String getFunctionName() {
      return functionName;
    }

    public void setFunctionName(String functionName) {
      this.functionName = functionName;
    }

  }

  public static class AstUnary extends AstNode {

    @Override
    public String toString() {
      return "AstUnary";
    }

  }
}