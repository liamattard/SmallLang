package app.lexer.filetools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

  Scanner smallLangScanner;
  List<Character> line;
  char rollbackChar;

  public FileReader(String filename) {
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
        final String data = smallLangScanner.nextLine();
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

        if (data.length() != 0) {
          line = new ArrayList<>();
          char[] letters = data.toCharArray();

          for (char c : letters) {
            line.add(c);
          }

          character = line.get(0);
          rollbackChar = line.get(0);
          line.remove(0);

        }

      } else {
        smallLangScanner.close();

      }
    }

    return character;
  }

  /**
   * aa.
   */
  public void rollBack() {
    line.add(0, rollbackChar);
  }
}