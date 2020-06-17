package app.visitor.models;

import java.util.Objects;

public class VariableIdentifier extends Identifier {


  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }
          
    if (!(o instanceof VariableIdentifier)) {
      return false;
    }
    
    VariableIdentifier variableIdentifier = (VariableIdentifier) o;

    return Objects.equals(getName(), variableIdentifier.getName());

  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  
}