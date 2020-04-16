package app.lexer.tables;

import app.lexer.models.StateType;

import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the Classificaiton Table.
 * On Creating a state you assign it one of the following enums
 * which will set the boolean accept state to its correspond value
 * 
 * <p>
 * <table border="1">
 * <tr>
 *      <td><b>State</b></td> <td><b>Accepted Or Not</b></td>
 * </tr>
 * <tr> 
 *      <td>START</td> <td>false</td> 
 * </tr>
 * <tr> 
 *      <td>DIGIT</td> <td>true<td>
 * </tr>
 * <tr> 
 *      <td>DIGITDOT</td> <td>false<td>
 * </tr>
 * <tr> 
 *      <td>FLOAT</td> <td>true<td>
 * </tr>
 * <tr> 
 *      <td>ERROR</td> <td>false<td>
 * </tr>
 * <tr> 
 *      <td>BAD</td> <td>false<td>
 * </tr>
 * </table>
 * </p>
 */
public class TransitionTable {

  static Map<StateType, State> transitions = new HashMap<StateType,State>();
  
  // TransitionTable
  static {

    // Start State
    transitions.put(new StateType(State.START,Type.DIGIT), State.DIGIT);
    transitions.put(new StateType(State.START,Type.DOT), State.ERROR);
    transitions.put(new StateType(State.START,Type.OTHER), State.ERROR);

    // Digit State
    transitions.put(new StateType(State.DIGIT,Type.DIGIT), State.DIGIT);
    transitions.put(new StateType(State.DIGIT,Type.DOT), State.DIGITDOT);
    transitions.put(new StateType(State.DIGIT,Type.OTHER), State.ERROR);

    // DigitDot State
    transitions.put(new StateType(State.DIGITDOT,Type.DIGIT), State.FLOAT);
    transitions.put(new StateType(State.DIGITDOT,Type.DOT), State.ERROR);
    transitions.put(new StateType(State.DIGITDOT,Type.OTHER), State.ERROR);
    
    // Float State
    transitions.put(new StateType(State.FLOAT,Type.DIGIT), State.FLOAT);
    transitions.put(new StateType(State.FLOAT,Type.DOT), State.ERROR);
    transitions.put(new StateType(State.FLOAT,Type.OTHER), State.ERROR);
        
    // Error State
    transitions.put(new StateType(State.ERROR,Type.DIGIT), State.ERROR);
    transitions.put(new StateType(State.ERROR,Type.DOT), State.ERROR);
    transitions.put(new StateType(State.ERROR,Type.OTHER), State.ERROR);
    

  }

  public static State transition(State state, Type type) {
    return transitions.get(new StateType(state, type));
  }
}