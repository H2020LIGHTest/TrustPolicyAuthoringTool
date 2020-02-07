package eu.lightest.tpat.mvc.view.DetailViews;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailTrustSchemeController;
import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.utils.DragDrop.Block;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DetailTrustSchemeView extends ADetailView {

  @FXML
  public TextField mSearchBox;

  @FXML
  public ListView<String> mListView;
  @FXML
  public Button mClearButton;


  private DetailTrustSchemeController mController = new DetailTrustSchemeController(this);

  public void initController(Block assocBlock, IBaseController baseController) {
    mController.init(assocBlock, baseController);
    mController.restoreDetailView();
  }

  @FXML
  void initialize() {
    mClearButton.getStyleClass().add("trans-button");
    mClearButton.setGraphic(new ImageView(new Image("images/USTUTT/24x24/Mülleimer_blau.png", 12, 12,true, true)));
    mClearButton.setOnAction(event -> mSearchBox.setText(""));
  }

  public TextField getSearchBox() {
    return mSearchBox;
  }

  public ListView<String> getListView() {
    return mListView;
  }
}
