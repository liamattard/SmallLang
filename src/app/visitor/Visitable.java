package app.visitor;

import org.w3c.dom.Element;

public interface Visitable {

  public Element accepts(Visitor visitor);
  
}