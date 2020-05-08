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
}