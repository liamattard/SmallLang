package app.lexer.models;

import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;

import java.util.Objects;


public class StateType {
  State state;
  Type type;
  State stateReturn;

  public StateType(State state, Type type) {
    this.state = state;
    this.type = type;
  }

  /**
   * aa.
   * @param state state
   * @param type  type
   * @param stateReturn statereturn
   */
  public StateType(State state, Type type, State stateReturn) {
    this.state = state;
    this.type = type;
    this.stateReturn = stateReturn;
  }

  public State getState() {
    return this.state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Type getType() {
    return this.type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public State getStateReturn() {
    return this.stateReturn;
  }

  public void setStateReturn(State stateReturn) {
    this.stateReturn = stateReturn;
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