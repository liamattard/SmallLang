package app.lexer.filetools;

import app.lexer.models.StateType;
import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader {
  /**
   * aa.
   */
  public static List<StateType> readcsv() {

    List<Type> types = new ArrayList<Type>();
    List<StateType> stateTypes = new ArrayList<StateType>();
    String csvFile = "src/app/lexer/csvfiles/TableV2.csv";
    BufferedReader br = null;
    String line = "";

    try {

      br = new BufferedReader(new FileReader(csvFile));

      String typesString = br.readLine();
      String[] typesArray = typesString.split(",");

      for (int i = 1; i < typesArray.length; i++) {
        types.add(Type.valueOf(typesArray[i]));
      }

      while ((line = br.readLine()) != null) {

        // use comma as separator
        String[] items = line.split(",");

        for (int i = 1; i < items.length; i++) {
          stateTypes.add(new StateType(State.valueOf(items[0]),
              types.get(i - 1), State.valueOf(items[i])));
        }

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return stateTypes;
  }
}