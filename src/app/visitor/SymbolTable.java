package app.visitor;

import app.parser.models.Type;
import app.visitor.models.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
  
  private List<Map<Identifier,Type>> symbolTable;   

  /**
   * Creates a new instance of a symbol Table.
   */
  public SymbolTable() {

    symbolTable = new ArrayList<Map<Identifier,Type>>();

  }

  /**
   * Creates and enters a new scope.
   */
  public void push() {

    Map<Identifier, Type> scope = new HashMap<Identifier,Type>();
    symbolTable.add(scope);

  }

  /**
   * Adds a new binding to the current scope.
   * @param identifier key to the map.
   * @param type type of the identifier.
   */
  public void insert(Identifier identifier, Type type) {

    Map<Identifier, Type> lastScope = symbolTable.get(symbolTable.size() - 1);
    lastScope.put(identifier, type);

  }

  /**
   * Find what entity a name corresponds to.
   * @param identifier key to the map.
   * @return Type of identifier.
   */
  public Type lookup(Identifier identifier) {

    boolean found = false;


    for (Map<Identifier,Type> map : symbolTable) {

      for (Map.Entry<Identifier,Type> entry: map.entrySet()) {

        if (entry.getKey().equals(identifier)) {
          found = true;
          return entry.getValue();
        }
      }

    }

    if (found == false) {

      System.out.println("Error, " + identifier.getName() + ", Not Found" );

    }

    return null;
  }

  /**
   * Leaves the current scope discarding all name bindings
   * in it.
   */
  public void pop() {

    symbolTable.remove(symbolTable.size() - 1);

  }

}
