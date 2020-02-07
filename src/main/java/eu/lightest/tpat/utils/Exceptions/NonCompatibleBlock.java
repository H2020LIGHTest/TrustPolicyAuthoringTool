package eu.lightest.tpat.utils.Exceptions;

public class NonCompatibleBlock extends UserException {

  private final String mNextBlockname;
  private final String mAcutalBlockName;

  public NonCompatibleBlock(String acutalBlockName, String nextBlockName){
    super(true);
    mAcutalBlockName = acutalBlockName;
    mNextBlockname = nextBlockName;
  }

  @Override
  public String getMessage() {
    return "A " + mNextBlockname + " block can not be connected to a " + mAcutalBlockName + " block. Please reconsider";
  }
}
