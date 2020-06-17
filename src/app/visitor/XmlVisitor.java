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

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlVisitor implements Visitor {

  Element root;
  Element current;
  private Visitor visitor;
  private Document doc;

  /**
   * Sets the Visitor Class and the Root Node to start parsing the tree using the
   * visitorclass.
   * 
   * @param rootNode the AstProgramNode.
   * @param visitor  the visitor class.
   * 
   */
  public void buildXml(AstNode rootNode, Visitor visitor) {
    try {

      this.visitor = visitor;

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      doc = docBuilder.newDocument();
      rootNode.accepts(visitor);
      doc.appendChild(root);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("src/app/visitor/xmlfiles/program.xml"));

      transformer.transform(source, result);

    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (TransformerException tfe) {
      tfe.printStackTrace();
    }

  }

  @Override
  public void visit(AstProgramNode programNode) {

    Element element = doc.createElement("Program");
    Attr attr = doc.createAttribute("name");

    attr.setValue(programNode.getName());
    element.setAttributeNode(attr);
    
    root = element;
    current = root;

    for (AstNode astNode : programNode.getChildren()) {
      astNode.accepts(visitor);
      current = root;
    }

  }

  @Override
  public void visit(AstVariableDeclNode variableDeclNode) {
    String type = variableDeclNode.getType().toString();
    visitWithOneAttr(variableDeclNode, "VariableDeclaration", "Type", type);
  }

  @Override
  public void visit(AstAssignmentNode assignmentNode) {
    visitWithNoAttr(assignmentNode, "AssignmentStatement");
  }

  @Override
  public void visit(AstBlock block) {

    Element blockElement = doc.createElement("Block");
    if (block.getValidity() == 1) {
      Attr validity = doc.createAttribute("Validity");
      validity.setValue("True");
      blockElement.setAttributeNode(validity);
    }

    if (block.getValidity() == 2) {
      Attr validity = doc.createAttribute("Validity");
      validity.setValue("False");
      blockElement.setAttributeNode(validity);
    }

    current.appendChild(blockElement);

    current = blockElement;
    for (AstNode astNode : block.getChildren()) {
      astNode.accepts(visitor);
      current = blockElement;
    }


  }

  @Override
  public void visit(AstIfStatement ifStatement) {

    visitWithNoAttr(ifStatement, "IfStatement");

  }

  @Override
  public void visit(AstAdditiveOp additiveOperator) {
    String operator = additiveOperator.getOperator().toString();
    visitWithOneAttr(additiveOperator, "AdditiveOp", "Operator", operator);

  }

  @Override
  public void visit(AstBoolLiteral boolLiteral) {

    String literal = boolLiteral.getLiteral();
    visitLiterals(boolLiteral, "BoolConst", literal);
  }

  @Override
  public void visit(AstFloatLiteral astFloatLiteral) {
    String literal = astFloatLiteral.getLiteral();
    visitLiterals(astFloatLiteral, "FloatConst", literal);
  }

  @Override
  public void visit(AstForStatement forStatement) {
    // TODO Auto-generated method stub
  }

  @Override
  public void visit(AstFunctionCall functionCall) {

    String functionName = functionCall.getFunctionName();

    visitWithOneAttr(functionCall, "FunctionCall", "name", functionName);
  }

  @Override
  public void visit(AstFunctionDecl functionDecl) {
    Element functionDeclElement = doc.createElement("FunctionDeclaration");
    Attr name = doc.createAttribute("name");
    Attr type = doc.createAttribute("type");

    name.setValue(functionDecl.getFunctionName());
    type.setValue(functionDecl.getType().toString());

    functionDeclElement.setAttributeNode(type);
    functionDeclElement.setAttributeNode(name);

    current.appendChild(functionDeclElement);

    current = functionDeclElement;
    for (AstNode astNode : functionDecl.getChildren()) {
      astNode.accepts(visitor);
      current = functionDeclElement;
    }

  }

  @Override
  public void visit(AstIntLiteral intLiteral) {

    String literal = intLiteral.getLiteral();
    visitLiterals(intLiteral, "IntegerConst", literal);
  }

  @Override
  public void visit(AstMultiplicativeOp multiplicativeOp) {

    String operator = multiplicativeOp.getOperator().toString();

    visitWithOneAttr(multiplicativeOp, "MultiplicativeOp", "operator", operator);
  }

  @Override
  public void visit(AstParam param) {

    Element paramElement = doc.createElement("Param");
    Attr name = doc.createAttribute("name");
    Attr type = doc.createAttribute("type");

    type.setValue(param.getT().toString());
    name.setValue(param.getName());
    paramElement.setAttributeNode(type);
    paramElement.setAttributeNode(name);
    current.appendChild(paramElement);

    current = paramElement;

    
    for (AstNode astNode : param.getChildren()) {
      astNode.accepts(visitor);
      current = paramElement;
    }


  }

  @Override
  public void visit(AstPrintNode printNode) {
    visitWithNoAttr(printNode, "PrintStatement");
  }

  @Override
  public void visit(AstRelationalOp relationalOp) {

    String operator = relationalOp.getOperator().toString();

    visitWithOneAttr(relationalOp, "RelationalOp", "operator", operator);
  }

  @Override
  public void visit(AstReturnNode returnNode) {
    visitWithNoAttr(returnNode, "ReturnNode");
  }

  @Override
  public void visit(AstUnary unary) {
    visitWithNoAttr(unary, "Unary");
  }

  @Override
  public void visit(IdentifierNode identifier) {
    String name = identifier.getName();
    visitLiterals(identifier, "Identifier", name);
  }

  private void visitLiterals(AstNode node, String nodeTitle, String literal) {

    Element element = doc.createElement(nodeTitle);
    element.setTextContent(literal);

    current.appendChild(element);

    current = element;

    for (AstNode astNode : node.getChildren()) {
      astNode.accepts(visitor);
      current = element;
    }

  }

  private void visitWithNoAttr(AstNode node, String nodeTitle) {

    Element element = doc.createElement(nodeTitle);
    current.appendChild(element);

    current = element;

    for (AstNode astNode : node.getChildren()) {
      astNode.accepts(visitor);
      current = element;
    }

  }

  private void visitWithOneAttr(AstNode node, String nodeTitle,
      String attrTitle, String attrValue) {

    
    Element element = doc.createElement(nodeTitle);
    Attr attr = doc.createAttribute(attrTitle);

    attr.setValue(attrValue);
    element.setAttributeNode(attr);
    current.appendChild(element);
    
    current = element;
    for (AstNode astNode : node.getChildren()) {
      astNode.accepts(visitor);
      current = element;
    }

  }
}