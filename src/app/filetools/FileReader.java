package app.filetools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader {

  public FileReader(String filename) {
    readFile(filename);
  }

  static Scanner smallLangScanner;
  static List<Character> word;

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
   * Returns the next character from the file (ignoring comments and whitespaces).
   * 
   * @return next char
   * 
   */
  public char nextChar() {
    char character = ' ';

    if (word != null) {

      if (!word.isEmpty()) {

        character = word.get(0);
        word.remove(0);

      } else {

        word = null;

      }
    }

    if (word == null) {

      if (smallLangScanner.hasNextLine()) {

        final String data = smallLangScanner.nextLine();

        String commentRegex = "^\\/\\/";

        final Pattern comment = Pattern.compile(commentRegex);
        final Matcher foundComment = comment.matcher(data);

        /*
         * Symbol Checker
         * 
         * final Pattern p = Pattern.compile("[^A-Za-z0-9.]"); final Matcher m =
         * p.matcher(data); Symbol Splitter final String[] x =
         * data.split("((?<=[^A-Za-z0-9.])|(?=[^A-Za-z0-9.]))");
         */

        if (!foundComment.find()) {

          word = new ArrayList<>();
          char[] letters = data.toCharArray();

          for (char c : letters) {
            word.add(c);
          }

          character = word.get(0);
          word.remove(0);

        }

      } else {
        
        smallLangScanner.close();

      }

    }

    if (character == ' ') {
      System.out.println("Character Not Found");
    }

    return character;
  }

}