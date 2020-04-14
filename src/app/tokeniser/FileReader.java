package app.tokeniser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader {

  /**
   * Reads a file and returns a scanner.
   * 
   * @param filename String representing name of File
   * @return Scanner of file
   * 
   */

  public Scanner readFile(String filename) {

    try {

      final File smallLangFile = new File("SmallLang/" + filename);
      final Scanner smallLangScanner = new Scanner(smallLangFile);

      smallLangScanner.close();

      return smallLangScanner;

    } catch (final FileNotFoundException e) {

      System.out.println("An error occurred when reading file: SmallLang/" + filename);
      e.printStackTrace();

    }
    
    return null;

  }

  /**
   * Extracts the user's name from the input arguments.
   * 
   */

  public String nextWord() {

    final Scanner myReader = readFile("example.SmallLang");
    while (myReader.hasNext()) {

      final String data = myReader.next();

      final Pattern comment = Pattern.compile("^\\/\\/");
      final Pattern p = Pattern.compile("[^A-Za-z0-9.]");
      final Matcher m = p.matcher(data);
      final Matcher foundComment = comment.matcher(data);

      if (!foundComment.find()) {
        if (m.find()) {

          final String[] x = data.split("((?<=[^A-Za-z0-9.])|(?=[^A-Za-z0-9.]))");
          for (final String string : x) {
            System.out.println(string);
          }
        } else {
          System.out.println(data);
        }
      }

    }

    return " ";
  }

}