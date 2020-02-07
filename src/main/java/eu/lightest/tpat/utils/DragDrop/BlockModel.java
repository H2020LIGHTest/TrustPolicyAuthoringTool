package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.model.DetailModels.IDetailModel;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.CategoryState;

public abstract class BlockModel implements java.io.Serializable{
  private IDetailModel mDetailModel;
  protected CategoryState mState;

  private Number mYpos;
  private Number mXpos;

  public BlockModel(IDetailModel detailModel, CategoryState state) {
    mDetailModel = detailModel;
    mState = state;
    mState.setBlock(this);

  }

  public CategoryState getState() {
    return mState;
  }

  public IDetailModel getDetailModel() {
    return mDetailModel;
  }

  public void setDetailModel(IDetailModel detailModel) {
    this.mDetailModel = detailModel;
  }

  public Number getmYpos() {
    return mYpos;
  }

  public void setmYpos(Number mYpos) {
    this.mYpos = mYpos;
  }

  public Number getmXpos() {
    return mXpos;
  }

  public void setmXpos(Number mXpos) {
    this.mXpos = mXpos;
  }
}
