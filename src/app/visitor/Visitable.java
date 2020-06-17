package app.visitor;


public interface Visitable {

  public void accepts(Visitor visitor);
  
}