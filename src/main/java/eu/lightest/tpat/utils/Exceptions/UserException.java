package eu.lightest.tpat.utils.Exceptions;

public abstract class UserException extends Exception {
  private boolean mIsError;

  public UserException(boolean isError) {
    mIsError = isError;
  }

  public boolean isError() {
    return mIsError;
  }
}
