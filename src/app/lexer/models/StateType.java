package app.lexer.models;

import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;

import java.util.Objects;


public class StateType {
  State state;
  Type type;

  public StateType(State state, Type type) {
    this.state = state;
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
          
    if (!(o instanceof StateType)) {
      return false;
    }
    StateType stateType = (StateType) o;
    return Objects.equals(state, stateType.state) && Objects.equals(type, stateType.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state, type);
  }

}