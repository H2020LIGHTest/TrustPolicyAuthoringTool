package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.model.DetailModels.IDetailModel;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.CategoryState;

public class SnapBlockModel extends BlockModel{
  public SnapBlockModel mNext;
  public SnapBlockModel mPrev;

  private transient SnapBlock mController;

  // Used for Reloading saves only
//  private static final long serialVersionUID = -5819635754292019550L;
  public SnapBlockModel(IDetailModel detailModel, CategoryState state, SnapBlockModel mNext, SnapBlockModel mPrev, SnapBlock mController) {
    super(detailModel, state);
    this.mNext = mNext;
    this.mPrev = mPrev;
    this.mController = mController;
  }

  public SnapBlockModel(CategoryState state, IDetailModel mDetailModel){
    super(mDetailModel, state);
  }

  public SnapBlock getController() {
    return mController;
  }

  public void setController(SnapBlock controller) {
    this.mController = controller;
  }
}
