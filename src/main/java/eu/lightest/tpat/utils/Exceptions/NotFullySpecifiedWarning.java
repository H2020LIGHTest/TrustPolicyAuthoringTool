package eu.lightest.tpat.utils.Exceptions;

public class NotFullySpecifiedWarning extends UserException {
  public NotFullySpecifiedWarning() {
    super(false);
  }

  @Override
  public String getMessage() {
    super.getMessage();
    return "One or more blocks are not fully specified";
  }
}
