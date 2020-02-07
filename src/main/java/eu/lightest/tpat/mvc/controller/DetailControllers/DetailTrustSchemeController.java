package eu.lightest.tpat.mvc.controller.DetailControllers;


import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.mvc.model.DetailModels.DetailTrustSchemeModel;
import eu.lightest.tpat.mvc.view.DetailViews.DetailTrustSchemeView;
import eu.lightest.tpat.utils.DragDrop.Block;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class DetailTrustSchemeController implements IDetailController {
  private Block mBlock;
  private DetailTrustSchemeModel mModel;
  private DetailTrustSchemeView mView;

  public DetailTrustSchemeController(DetailTrustSchemeView detailTrustSchemeView) {
    mView = detailTrustSchemeView;
  }

  @Override
  public void init(Block assocBlock, IBaseController baseController) {
    mBlock = assocBlock;
    if (mBlock.getBlockModel().getDetailModel() == null)
      mBlock.getBlockModel().setDetailModel(new DetailTrustSchemeModel());
    mModel = (DetailTrustSchemeModel) mBlock.getBlockModel().getDetailModel();

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

//    sortedData.comparatorProperty().bind(mView.mListView.comparatorProperty());

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
    mModel.mTrustScheme = name;
    mBlock.setHeaderText(mModel.getSubState().getName());
  }

  @Override
  public void restoreDetailView() {
    mView.getListView().getSelectionModel().select(mModel.mTrustScheme);
    mView.getSearchBox().setText(mModel.mTrustScheme);
  }
}
