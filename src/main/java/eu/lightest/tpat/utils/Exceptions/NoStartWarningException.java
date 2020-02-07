package eu.lightest.tpat.utils.Exceptions;

public class NoStartWarningException extends UserException {
  public NoStartWarningException() {
    super(false);
  }

  @Override
  public String getMessage() {
    super.getMessage();
    return "At least one IF needed to mark the start of the rule";
  }
}
