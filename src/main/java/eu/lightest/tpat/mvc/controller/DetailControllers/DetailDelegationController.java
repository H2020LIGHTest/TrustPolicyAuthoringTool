package eu.lightest.tpat.mvc.controller.DetailControllers;


import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.mvc.model.DetailModels.DetailDelegationModel;
import eu.lightest.tpat.mvc.view.DetailViews.DetailDelegationView;
import eu.lightest.tpat.utils.DragDrop.Block;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class DetailDelegationController implements IDetailController {
  private Block mBlock;
  private DetailDelegationModel mModel;
  private DetailDelegationView mView;

  public DetailDelegationController(DetailDelegationView detailTrustSchemeView) {
    mView = detailTrustSchemeView;
  }

  @Override
  public void init(Block assocBlock, IBaseController baseController) {
    mBlock = assocBlock;
    if (mBlock.getBlockModel().getDetailModel() == null)
      mBlock.getBlockModel().setDetailModel(new DetailDelegationModel());
    mModel = (DetailDelegationModel) mBlock.getBlockModel().getDetailModel();

    filterLogic(baseController);
  }

  private void filterLogic(IBaseController baseController) {
    FilteredList<String> filteredData = new FilteredList<>(FXCollections.observableList(mModel.getComboList()), p -> true);

    mView.mSearchBox.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(person -> {
      // If filter text is empty, display all persons.
      if (newValue == null || newValue.isEmpty()) {
        return true;
      } else return person.toLowerCase().contains(newValue.toLowerCase());
    }));

    SortedList<String> sortedData = new SortedList<>(filteredData);

    mView.getListView().setItems(sortedData);

    mView.getListView().getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
      if (newValue != null) {
        syncLabel(newValue);
        baseController.refreshTPLandSave(false);
      }
    });
  }

  @Override
  public void syncLabel(String name) {
    mModel.mDelegationScheme = name;
    mBlock.setHeaderText(mModel.getSubState().getName());
  }

  @Override
  public void restoreDetailView() {
    mView.getListView().getSelectionModel().select(mModel.mDelegationScheme);
//    mView.getSearchBox().setText(mModel.mDelegationScheme);
  }
}
