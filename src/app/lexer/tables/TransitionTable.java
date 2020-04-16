package app.lexer.tables;

import app.lexer.filetools.CsvFileReader;
import app.lexer.models.StateType;

import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransitionTable {

  static Map<StateType, State> transitions = new HashMap<StateType,State>();
  
  // TransitionTable
  static {

    List<StateType> stateTypes = CsvFileReader.readcsv();

    for (StateType stateType : stateTypes) {
      
      transitions.put(new StateType(stateType.getState(),stateType.getType()),
          stateType.getStateReturn());

    }

  }

  public static State transition(State state, Type type) {
    return transitions.get(new StateType(state, type));
  }
}