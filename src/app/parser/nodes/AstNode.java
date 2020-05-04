package app.parser.nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class AstNode {

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
