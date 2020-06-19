package app.lexer.tables;

/**
 * This class represents the Classificaiton Table. On Creating a state you
 * assign it one of the following enums which will set the boolean accept state
 * to its correspond value
 * 
 * <p>
 * <table border="1">
 * <tr>
 * <td><b>State</b></td>
 * <td><b>Accepted Or Not</b></td>
 * </tr>
 * <tr>
 * <td>START</td>
 * <td>false</td>
 * </tr>
 * <tr>
 * <td>DIGIT</td>
 * <td>true
 * <td>
 * </tr>
 * <tr>
 * <td>DIGITDOT</td>
 * <td>false
 * <td>
 * </tr>
 * <tr>
 * <td>FLOAT</td>
 * <td>true
 * <td>
 * </tr>
 * <tr>
 * <td>ERROR</td>
 * <td>false
 * <td>
 * </tr>
 * <tr>
 * <td>BAD</td>
 * <td>false
 * <td>
 * </tr>
 * *
 * <tr>
 * <td>IDENIFIER</td>
 * <td>true
 * <td>
 * </tr>
 * </table>
 * </p>
 */
public class TokenTypeTable {
  public enum State {
  
    START(false),
    DIGIT(true),
    IDENTIFIER(true),
    SYMBOL(true),
    DIGITDOT(false),
    FLOAT(true),
    ERROR(false),
    EQUALS(true),
    SLASH(true),
    COMMENT(true),
    GREATERTHAN(true),
    SMALLERTHAN(true),
    RELATION(true),
    BAD(false),
    CHARONE(false),
    CHARTWO(false),
    CHAR(true);
    
    final boolean acceptState;

    private State(boolean acceptState) {
      this.acceptState = acceptState;
    }
  
    public boolean isAcceptState() {
      return acceptState;
    }
  
  }
  
}