package eu.lightest.tpat.utils.Exceptions;

public class NoDomainChosenWarning extends UserException {
  public NoDomainChosenWarning() {
    super(false);
  }

  @Override
  public String getMessage() {
    super.getMessage();
    return "There is no domain chosen";
  }
}
