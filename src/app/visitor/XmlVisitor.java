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
import java.util.List;

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
      Element rootElement = rootNode.accepts(visitor);
      doc.appendChild(rootElement);

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
  public Element visit(AstProgramNode programNode) {
    return visitWithOneAttr(programNode, "Program", "name", programNode.getName());
  }

  @Override
  public Element visit(AstVariableDeclNode variableDeclNode) {
    String type = variableDeclNode.getType().toString();
    return visitWithOneAttr(variableDeclNode, "VariableDeclaration", "Type", type);
  }

  @Override
  public Element visit(AstAssignmentNode assignmentNode) {
    return visitWithNoAttr(assignmentNode, "AssignmentStatement");
  }

  @Override
  public Element visit(AstBlock block) {

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

    for (AstNode astNode : block.getChildren()) {
      blockElement.appendChild(astNode.accepts(visitor));
    }

    return blockElement;

  }

  @Override
  public Element visit(AstIfStatement ifStatement) {

    return visitWithNoAttr(ifStatement, "IfStatement");

  }

  @Override
  public Element visit(AstAdditiveOp additiveOperator) {
    String operator = additiveOperator.getOperator().toString();
    return visitWithOneAttr(additiveOperator, "AdditiveOp", "Operator", operator);

  }

  @Override
  public Element visit(AstBoolLiteral boolLiteral) {

    String literal = boolLiteral.getLiteral();
    return visitLiterals(boolLiteral, "BoolConst", literal);
  }

  @Override
  public Element visit(AstFloatLiteral astFloatLiteral) {
    String literal = astFloatLiteral.getLiteral();
    return visitLiterals(astFloatLiteral, "FloatConst", literal);
  }

  @Override
  public Element visit(AstForStatement forStatement) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Element visit(AstFunctionCall functionCall) {

    String functionName = functionCall.getFunctionName();

    return visitWithOneAttr(functionCall, "FunctionCall", "name", functionName);
  }

  @Override
  public Element visit(AstFunctionDecl functionDecl) {
    Element functionDeclElement = doc.createElement("FunctionDeclaration");
    Attr name = doc.createAttribute("name");
    Attr type = doc.createAttribute("type");

    name.setValue(functionDecl.getFunctionName());
    type.setValue(functionDecl.getType().toString());

    functionDeclElement.setAttributeNode(type);
    functionDeclElement.setAttributeNode(name);

    for (AstNode astNode : functionDecl.getChildren()) {
      functionDeclElement.appendChild(astNode.accepts(visitor));
    }

    return functionDeclElement;
  }

  @Override
  public Element visit(AstIntLiteral intLiteral) {

    String literal = intLiteral.getLiteral();
    return visitLiterals(intLiteral, "IntegerConst", literal);
  }

  @Override
  public Element visit(AstMultiplicativeOp multiplicativeOp) {

    String operator = multiplicativeOp.getOperator().toString();

    return visitWithOneAttr(multiplicativeOp, "MultiplicativeOp", "operator", operator);
  }

  @Override
  public Element visit(AstParam param) {

    Element paramElement = doc.createElement("Param");
    Attr name = doc.createAttribute("name");
    Attr type = doc.createAttribute("type");

    type.setValue(param.getT().toString());
    name.setValue(param.getName());
    paramElement.setAttributeNode(type);
    paramElement.setAttributeNode(name);

    for (AstNode astNode : param.getChildren()) {
      paramElement.appendChild(astNode.accepts(visitor));
    }

    return paramElement;

  }

  @Override
  public Element visit(AstPrintNode printNode) {
    return visitWithNoAttr(printNode, "PrintStatement");
  }

  @Override
  public Element visit(AstRelationalOp relationalOp) {

    String operator = relationalOp.getOperator().toString();

    return visitWithOneAttr(relationalOp, "RelationalOp", "operator", operator);
  }

  @Override
  public Element visit(AstReturnNode returnNode) {
    return visitWithNoAttr(returnNode, "ReturnNode");
  }

  @Override
  public Element visit(AstUnary unary) {
    return visitWithNoAttr(unary, "Unary");
  }

  @Override
  public Element visit(IdentifierNode identifier) {
    String name = identifier.getName();
    return visitLiterals(identifier, "Identifier", name);
  }

  private Element visitLiterals(AstNode node, String nodeTitle,
      String literal) {

    Element assignmentElement = doc.createElement(nodeTitle);
    assignmentElement.setTextContent(literal);

    for (AstNode astNode : node.getChildren()) {
      assignmentElement.appendChild(astNode.accepts(visitor));
    }

    return assignmentElement;
  }

  private Element visitWithNoAttr(AstNode node, String nodeTitle) {

    Element assignmentElement = doc.createElement(nodeTitle);

    for (AstNode astNode : node.getChildren()) {
      assignmentElement.appendChild(astNode.accepts(visitor));
    }

    return assignmentElement;

  }

  private Element visitWithOneAttr(AstNode node,
      String nodeTitle, String attrTitle, String attrValue) {

    Element element = doc.createElement(nodeTitle);
    Attr attr = doc.createAttribute(attrTitle);

    attr.setValue(attrValue);
    element.setAttributeNode(attr);

    for (AstNode astNode : node.getChildren()) {
      element.appendChild(astNode.accepts(visitor));
    }

    return element;

  }
}