package app.parser.models;

import java.util.HashMap;
import java.util.Map;

public enum OperatorTypes {
  // Multiplicative Op
  TIMES, DIVISION, AND,
  
  // AdditiveOp
  PLUS, MINUS, OR,

  // Relational OP
  SMALLERTHAN, GREATERTHAN, EQUALSTO,
  NOTEQUALTO, SMALLEROREQUAL, GREATEROREQUAL;

  /**
   * return Operator enum type from string.
   * 
   * @param op operator string.
   */
  public static OperatorTypes getOperator(String op) {

    Map<String, OperatorTypes> stringByOperator = new HashMap<String, OperatorTypes>(); 

    // Muliplicative Op
    stringByOperator.put("*", TIMES);
    stringByOperator.put("/", DIVISION);
    stringByOperator.put("and", AND);

    // Additive Op
    stringByOperator.put("+", PLUS);
    stringByOperator.put("-", MINUS);
    stringByOperator.put("or", OR);

    // Relational Op
    stringByOperator.put("<", SMALLERTHAN);
    stringByOperator.put(">", GREATERTHAN);
    stringByOperator.put("==", EQUALSTO);
    stringByOperator.put(">=", GREATEROREQUAL);
    stringByOperator.put("<=", SMALLEROREQUAL);
    stringByOperator.put("<>", NOTEQUALTO);
    

    return stringByOperator.get(op);
  }
}