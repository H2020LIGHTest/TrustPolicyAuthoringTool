package eu.lightest.tpat.mvc.controller.DetailControllers;

import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.mvc.model.DetailModels.DetailValuesModel;
import eu.lightest.tpat.mvc.view.DetailViews.DetailValuesView;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.*;
import eu.lightest.tpat.utils.TreeItemModel;
import javafx.scene.control.TreeItem;

import java.time.LocalDate;

public class DetailValuesController implements IDetailController{
  private final DetailValuesView mView;
  private Block mBlock;
  private DetailValuesModel mModel;

  private IBaseController mBaseController;

  public DetailValuesController(DetailValuesView detailValuesView) {
    mView = detailValuesView;
  }

  public void init(Block assocBlock, IBaseController baseController) {
    mBaseController = baseController;
    mBlock = assocBlock;
    if (mBlock.getBlockModel().getDetailModel() == null)
      mBlock.getBlockModel().setDetailModel(new DetailValuesModel());
    mModel = (DetailValuesModel) mBlock.getBlockModel().getDetailModel();
  }

  public void setValueChoice(AValueSubState state) {
    mModel.setValueSubState(state);
    mBaseController.refreshTPLandSave(true);
      syncLabel(state.getName());
  }

  @Override
  public void syncLabel(String name) {
    mBlock.setHeaderText(name);
  }

  @Override
  public void restoreDetailView() {
    if (mModel.getSubState() != null) {
      mModel.getSubState().callCorrespondingDetailView(mView);
    }
  }

  public void setChosenAmount(String value) {
    ((AmountSubState)mModel.getSubState()).setValue(value);
    refreshContext();
  }
  public void setChosenDate(LocalDate value) {
    ((DateSubState)mModel.getSubState()).setValue(value);
    refreshContext();
  }
  public void setChosenText(String value) {
    ((TextSubState)mModel.getSubState()).setValue(value);
    refreshContext();
  }
  public void setChosenExtracted(TreeItem<TreeItemModel> value) {
    setValueChoice(new ExtractedSubState());
    ((ExtractedSubState)mModel.getSubState()).setValue(value.getValue());
    refreshContext();
  }

  private void refreshContext() {
    mBaseController.refreshTPLandSave(true);
    syncLabel(mModel.getSubState().getName());
  }

  public DetailValuesModel getModel() {
    return mModel;
  }

  public IBaseController getBaseController() {
    return mBaseController;
  }
}
