package app.lexer.filetools;

import app.lexer.tables.ClassifierTable;
import app.lexer.tables.ClassifierTable.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SmallLangReader {

  private Scanner smallLangScanner;
  private List<Character> line;
  private char rollbackChar;
  private boolean closed = false;

  public SmallLangReader(String filename) {
    readFile(filename);
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
        character = line.get(0);
        rollbackChar = line.get(0);
        line.remove(0);

      } else {
        line = null;

      }
    }

    // If line is null, load next line...
    if (line == null) {
      if (smallLangScanner.hasNextLine()) {

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
        }

        line = new ArrayList<>();
        char[] letters = data.toCharArray();

        for (char c : letters) {
          line.add(c);
        }
        line.add('\n');

        character = line.get(0);
        rollbackChar = line.get(0);
        line.remove(0);

      } else {
        System.out.println("aaaaaa");
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
  public void rollBack() {
    if (closed == false && ClassifierTable.getType(rollbackChar) != Type.WHITESPACE
        && ClassifierTable.getType(rollbackChar) != Type.NEWLINE) {
      line.add(0, rollbackChar);
    }

  }
}