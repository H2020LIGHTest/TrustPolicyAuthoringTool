package eu.lightest.tpat.utils.Exceptions;

public class DoubleStartWarningException extends UserException {
  public DoubleStartWarningException() {
    super(false);
  }

  @Override
  public String getMessage() {
    super.getMessage();
    return
        "Only one IF allowed, due to possible ambiguity.";
  }
}
