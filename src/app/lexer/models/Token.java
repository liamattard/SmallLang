package app.lexer.models;


public class Token {

  Attributes attributes;
  TokenType tokenType;

  public Token(){}

  public Token(Attributes attributes, TokenType tokenType) {
    this.attributes = attributes;
    this.tokenType = tokenType;
  }

  public Attributes getAttributes() {
    return this.attributes;
  }

  public TokenType getTokenType() {
    return this.tokenType;
  }

  public void setTokenType(TokenType tokenType) {
    this.tokenType = tokenType;
  }

  public void setAttributes(Attributes attributes) {
    this.attributes = attributes;
  }
  

  @Override
  public String toString() {
    return "{" 
      + " attributes='" + getAttributes() + "'" 
      + ", tokenType='" + getTokenType() + "'" 
      + "}";
  }

  
}