package app.lexer.filetools;

import app.lexer.tables.ClassifierTable;
import app.lexer.tables.ClassifierTable.Type;
import app.lexer.tables.TokenTypeTable.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SmallLangReader {

  private int lineNumber = 0;
  private int characterNumber = 0;
  private Scanner smallLangScanner;
  private List<Character> line;
  private List<Character> rollBackChar = new ArrayList<Character>();
  private boolean closed = false;

  public SmallLangReader(String filename) {
    readFile(filename);
  }


  public int getLineNumber() {
    return this.lineNumber;
  }

  public int getCharacterNumber() {
    return this.characterNumber;
  }

  /**
   * Reads a file and returns a scanner.
   * 
   * @param filename String representing name of File
   * 
   */
  public void readFile(String filename) {

    try {
      final File smallLangFile = new File("SmallLang/" + filename);
      smallLangScanner = new Scanner(smallLangFile);

    } catch (final FileNotFoundException e) {

      System.out.println("An error occurred when reading file: SmallLang/" + filename);
      e.printStackTrace();

    }

  }

  /**
   * Stores the current File in the FileReader class properties and iterates
   * through, then returns the corresponding character everytime that the method
   * is called.
   * 
   * @return next char from file
   * 
   */
  public char nextChar() {
    char character = Character.MIN_VALUE;

    if (line != null) {
      // If line is not empty, load characters from the line
      if (!line.isEmpty()) {
        characterNumber += 1;
        character = line.get(0);
        rollBackChar.add(line.get(0));
        line.remove(0);

      } else {
        characterNumber = 0;
        line = null;

      }
    }

    // If line is null, load next line...
    if (line == null) {
      if (smallLangScanner.hasNextLine()) {
        lineNumber += 1;
        String data = smallLangScanner.nextLine();
        /*
         * Comment Finder String commentRegex = "^\\/\\/"; final Pattern comment =
         * Pattern.compile(commentRegex); final Matcher foundComment =
         * comment.matcher(data);
         *
         * Symbol Checker
         * 
         * final Pattern p = Pattern.compile("[^A-Za-z0-9.]"); final Matcher m =
         * p.matcher(data); Symbol Splitter final String[] x =
         * data.split("((?<=[^A-Za-z0-9.])|(?=[^A-Za-z0-9.]))");
         */
        while (data.length() == 0) {
          data = smallLangScanner.nextLine();
          lineNumber += 1;
        }

        line = new ArrayList<>();
        char[] letters = data.toCharArray();

        for (char c : letters) {
          line.add(c);
        }
        line.add('\n');
        characterNumber += 1;
        character = line.get(0);
        rollBackChar.add(line.get(0));
        line.remove(0);

      } else {
        closed = true;
        smallLangScanner.close();

      }
    }

    return character;
  }

  /**
   * Adds lost character back to the list, unless it is a whitespace in which it
   * is ignored.
   */
  public void rollBack(State state) {

    if (closed == false && rollBackChar.size() != 0) {
      char rbc = rollBackChar.get(rollBackChar.size() - 1);
      rollBackChar.remove(rollBackChar.size() - 1);
      if (ClassifierTable.getType(rbc) != Type.WHITESPACE
          && ClassifierTable.getType(rbc) != Type.NEWLINE) {
        if (state == State.START) {
          System.out.println("ERROR");
        }
        line.add(0,rbc);
      }
    }
  }

  /**
   * Clears the accepted token from the rollback char.
   * @param word the lexeme of the token
   */
  public void clearRollBack(String word) {
    for (int i = 0; i < word.length(); i++) {
      rollBackChar.remove(0);
      
    }
  }

  /**
   * Checks if the rollback list is empty.
   * @return boolean
   */
  public boolean checkIfEmpty() {
    if (rollBackChar.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
}