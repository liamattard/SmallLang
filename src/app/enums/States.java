package app.enums;

public enum States {

  STARTSTATE(false),
  INTSTATE(true),
  INTDOTSTATE(false),
  FLOATSTATE(true),
  ERRORSTATE(false),
  STATEBAD(false);

  States integer;
  States dot;
  States letter;

  final boolean acceptState;

  private States(boolean acceptState) {
    this.acceptState = acceptState;
  }

  public boolean isAcceptState() {
    return acceptState;
  }

  static {

    STARTSTATE.integer = INTSTATE;
    STARTSTATE.dot = ERRORSTATE;
    STARTSTATE.letter = ERRORSTATE;

    INTSTATE.integer = INTSTATE;
    INTSTATE.dot = INTDOTSTATE;
    INTSTATE.letter = ERRORSTATE;

    INTDOTSTATE.integer = FLOATSTATE;
    INTDOTSTATE.dot = ERRORSTATE;
    INTDOTSTATE.letter = ERRORSTATE;

    FLOATSTATE.integer = FLOATSTATE;
    FLOATSTATE.dot = ERRORSTATE;
    FLOATSTATE.letter = ERRORSTATE;

    ERRORSTATE.integer = ERRORSTATE;
    ERRORSTATE.dot = ERRORSTATE;
    ERRORSTATE.letter = ERRORSTATE;

  }

  // States start;
  // States integer;
  // States decimal;
  // States floatNum;

  // static{
  // Q0.start = Q1;
  // }

}
