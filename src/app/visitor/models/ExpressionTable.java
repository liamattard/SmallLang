package app.visitor.models;

import app.parser.models.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpressionTable {

  private List<ExpressionType[]> expression;

  /**
   * Creates a new List with ExpressionType array of size 3.
   */
  public ExpressionTable() {

    expression = new ArrayList<>();
    ExpressionType[] types = new ExpressionType[3];
    expression.add(types);

  }

  /**
   * Adds the type to the first empty element in the last array item of the
   * expression list.
   */
  public void add(ExpressionType type) {
    ExpressionType[] lastArray = expression.get(expression.size() - 1);
    
    int i = 0;

    while(i<=2){
    
      if (lastArray[i] == null) {

        lastArray[i] = type;
        i = 3;

      } 

      i++;

    }
  }

  /**
   * Check if List is Empty.
   * 
   * @return Boolean.
   */
  public boolean isEmpty() {
    if (expression.size() == 1 && expression.get(0)[0] == null) {
      return true;
    } else {
      return false;
    }
  }

  public void pop() {
    expression.remove(expression.size() - 1);
  }

  public void push() {
    ExpressionType[] array = new ExpressionType[3];
    expression.add(array);
  }

  /**
   * Gets the resulting type based on the array.
   * 
   * @return type based on the array.
   */
  public ExpressionType getType() {

    
    ExpressionType[] lastArray = expression.get(expression.size() - 1);

    ExpressionType operator = lastArray[0];
    ExpressionType typeOne = lastArray[1];
    ExpressionType typeTwo = lastArray[2];

    if (operator == ExpressionType.RELOP) {

      if (typeOne == ExpressionType.INT && typeTwo == ExpressionType.INT) {

        return ExpressionType.BOOL;

      } else if (typeOne == ExpressionType.FLOAT && typeTwo == ExpressionType.FLOAT) {

        return ExpressionType.BOOL;

      } else if (typeOne == ExpressionType.BOOL && typeTwo == ExpressionType.BOOL) {

        return ExpressionType.BOOL;

      } else {

        System.out.println("ERROR IN TYPE RELOP");
        return null;

      }

    }

    if (operator == ExpressionType.MULOP) {

      if (typeOne == ExpressionType.INT && typeTwo == ExpressionType.INT) {

        return ExpressionType.INT;

      } else if (typeOne == ExpressionType.FLOAT && typeTwo == ExpressionType.FLOAT) {

        return ExpressionType.FLOAT;

      } else if (typeOne == ExpressionType.FLOAT && typeTwo == ExpressionType.INT) {

        return ExpressionType.FLOAT;

      } else if (typeOne == ExpressionType.INT && typeTwo == ExpressionType.FLOAT) {

        return ExpressionType.FLOAT;

      } else {

        System.out.println("ERROR IN TYPE");
        return null;

      }

    }

    if (operator == ExpressionType.ADDOP) {

      if (typeOne == ExpressionType.INT && typeTwo == ExpressionType.INT) {

        return ExpressionType.INT;

      } else if (typeOne == ExpressionType.FLOAT && typeTwo == ExpressionType.FLOAT) {

        return ExpressionType.FLOAT;

      } else {

        System.out.println("ERROR IN TYPE");
        return null;

      }

    }

    if (operator == ExpressionType.AND) {

      if (typeOne == ExpressionType.BOOL && typeTwo == ExpressionType.BOOL) {

        return ExpressionType.BOOL;

      } else {

        System.out.println("ERROR");
        return null;
      }
    }

    if (operator == ExpressionType.OR) {

      if (typeOne == ExpressionType.BOOL && typeTwo == ExpressionType.BOOL) {

        return ExpressionType.BOOL;

      } else {

        System.out.println("ERROR");
        return null;

      }

    }

    if (operator == ExpressionType.FLOAT) {

      if (typeOne == null && typeTwo == null) {

        return ExpressionType.FLOAT;

      } else {

        System.out.println("ERROR");
        return null;

      }

    }

    if (operator == ExpressionType.BOOL) {

      if (typeOne == null && typeTwo == null) {

        return ExpressionType.BOOL;

      } else {

        System.out.println("ERROR");
        return null;

      }

    }

    if (operator == ExpressionType.INT) {

      if (typeOne == null && typeTwo == null) {

        return ExpressionType.INT;

      } else {

        System.out.println("ERROR");
        return null;

      }

    }

    return null;
  }

}
