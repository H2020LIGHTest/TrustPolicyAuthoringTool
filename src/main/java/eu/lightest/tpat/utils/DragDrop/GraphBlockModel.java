package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.model.DetailModels.IDetailModel;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.CategoryState;

public class GraphBlockModel extends BlockModel {
  private transient GraphBlock mController;

  public GraphBlockModel(IDetailModel mDetailModel, CategoryState mState) {
    super(mDetailModel, mState);
  }

  public void setBlockController(GraphBlock blockController) {
    this.mController = blockController;
  }

  public GraphBlock getGraphBlockController() {
    return mController;
  }
}
