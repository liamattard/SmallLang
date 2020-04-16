package app.lexer.tables;


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
 *  * <tr> 
 *      <td>IDENIFIER</td> <td>true<td>
 * </tr>
 * </table>
 * </p>
 */
public class ClassifierTable {

  public enum Type {
    DIGIT,
    DOT,
    LETTER,
    SYMBOL,
    OTHER;
  }

  /**
   * Returns the token type chosen from the Type class.
   * @param c character to check it's type
   * @return Type based on character
   */
  public static Type getType(char c) {
    if (Character.isDigit(c)) {
      return Type.DIGIT;
    } else if (Character.isLetter(c) || c == '_') {
      return Type.LETTER;
    } else if (c == '.') {
      return Type.DOT;
    } else if (c == ' ') {
      return Type.OTHER;
    } else if (c == '(' || c == ')' || c == '{' || c == '}' || c == ';' || c == ':') {
      return Type.SYMBOL;
    }
    return Type.OTHER;
  }

}