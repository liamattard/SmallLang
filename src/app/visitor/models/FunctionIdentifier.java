package app.visitor.models;

import app.parser.models.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class FunctionIdentifier extends Identifier {

  Map<VariableIdentifier, Type> parameters;

  /**
   * Constructor which declares parameters to a new arraylist.
   */
  public FunctionIdentifier() {

    parameters = new HashMap<VariableIdentifier, Type>();

  }

  /**
   * Add a new Parameter with its type to the function.
   * @param identifier type name.
   * @param type type of the parameter.
   */
  public void addParameter(VariableIdentifier identifier, Type type) {

    parameters.put(identifier, type);

  }


  public Map<VariableIdentifier,Type> getParameters() {
    return this.parameters;
  }
  

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }
          
    if (!(o instanceof FunctionIdentifier)) {
      return false;
    }
    
    FunctionIdentifier functionIdentifier = (FunctionIdentifier) o;

    return Objects.equals(getName(), functionIdentifier.getName())
        && (getParameters().equals(functionIdentifier.getParameters()));

  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getParameters());
  }

  

}