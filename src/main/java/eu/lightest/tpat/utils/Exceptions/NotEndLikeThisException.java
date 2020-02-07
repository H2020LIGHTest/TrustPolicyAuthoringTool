package eu.lightest.tpat.utils.Exceptions;

import java.util.List;

public class NotEndLikeThisException extends UserException {

  private final String mActualBlockName;
  private List<String> mPossibilities;

  public NotEndLikeThisException(String actualBlockName, List<String> possibilities){
    super(false);
    mActualBlockName = actualBlockName;
    mPossibilities = possibilities;
  }

  @Override
  public String getMessage() {
    StringBuilder s = new StringBuilder("A " + mActualBlockName + " can not mark an ending. Try ");
    int index = 1;
    for (String p : mPossibilities)
      if(index++ < mPossibilities.size() || mPossibilities.size() == 1) {
        s.append("a ").append(p).append(", ");
      }
      else {
        s.append("or a ").append(p).append(".");
      }
    return s.toString();
  }
}
