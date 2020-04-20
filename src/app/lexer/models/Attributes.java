package app.lexer.models;

public class Attributes{
  String lexeme;
  int line;
  int character;
  
  public Attributes(String lexeme, int line, int character){
    this.lexeme = lexeme;
    this.line = line;
    this.character = character;
  }


  public String getLexeme() {
    return this.lexeme;
  }

  public int getLine() {
    return this.line;
  }

  public int getCharacter() {
    return this.character;
  }

  @Override
  public String toString() {
    return "{" +
      " lexeme='" + getLexeme() + "'" +
      ", line='" + getLine() + "'" +
      ", character='" + getCharacter() + "'" +
      "}";
  }


}