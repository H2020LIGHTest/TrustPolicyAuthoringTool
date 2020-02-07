package eu.lightest.tpat.mvc.controller.DetailControllers;

import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.mvc.model.DetailModels.DetailExpressionModel;
import eu.lightest.tpat.mvc.view.DetailViews.DetailExpressionsView;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.ExpressionalSubState;

public class DetailExpressionsController implements IDetailController{
  private DetailExpressionsView mView;
  private Block mBlock;
  private DetailExpressionModel mModel;
  private IBaseController mBaseController;

  public DetailExpressionsController(DetailExpressionsView detailConditionalsView) {
    mView = detailConditionalsView;
  }

  @Override
  public void init(Block assocBlock, IBaseController baseController) {
    mBaseController = baseController;
    mBlock = assocBlock;
    if (mBlock.getBlockModel().getDetailModel() == null)
      mBlock.getBlockModel().setDetailModel(new DetailExpressionModel());
    mModel = (DetailExpressionModel) mBlock.getBlockModel().getDetailModel();
  }

  public void setChoice(ExpressionalSubState state) {
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
