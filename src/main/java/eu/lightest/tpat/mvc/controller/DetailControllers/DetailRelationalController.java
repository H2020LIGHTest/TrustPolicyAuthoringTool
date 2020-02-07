package eu.lightest.tpat.mvc.controller.DetailControllers;

import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.mvc.model.DetailModels.DetailRelationalModel;
import eu.lightest.tpat.mvc.view.DetailViews.DetailRelationalsView;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates.RelationalSubState;

public class DetailRelationalController implements IDetailController{
  private DetailRelationalsView mView;
  private Block mBlock;
  private DetailRelationalModel mModel;
  private IBaseController mBaseController;

  public DetailRelationalController(DetailRelationalsView detailConditionalsView) {
    mView = detailConditionalsView;
  }

  @Override
  public void init(Block assocBlock, IBaseController baseController) {
    mBaseController = baseController;
    mBlock = assocBlock;
    if (mBlock.getBlockModel().getDetailModel() == null)
      mBlock.getBlockModel().setDetailModel(new DetailRelationalModel());
    mModel = (DetailRelationalModel) mBlock.getBlockModel().getDetailModel();
  }

  public void setChoice(RelationalSubState state) {
    syncLabel(state.getName());
    mModel.mState = state;
    mBaseController.refreshTPLandSave(true);
  }

  @Override
  public void syncLabel(String name) {
    mBlock.setHeaderText(name);
  }

  @Override
  public void restoreDetailView() {
    if (mModel.mState != null) {
      mView.setRadioGroup(mModel.mState);
      //TODO detail view of detailview
    }
  }
}
