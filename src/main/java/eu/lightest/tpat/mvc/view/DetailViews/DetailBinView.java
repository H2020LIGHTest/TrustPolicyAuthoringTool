package eu.lightest.tpat.mvc.view.DetailViews;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailBinController;
import eu.lightest.tpat.utils.DragDrop.DragDropHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DetailBinView {
  DetailBinController mController = new DetailBinController(this);

  @FXML
  ImageView mBinImgView;

  @FXML
  Label mBinLabel;

  @FXML
  public void initialize() {
    mBinLabel.setText("\uD83D\uDDD1"); // waste basket utf-8
    mBinImgView.setImage(new Image("images/USTUTT/64x64/white_trash.png"));
  }

  public void initController(DragDropHandler dragDropHandler) {
    mController.init(dragDropHandler);
  }

  public Label getBinLabel() {
    return mBinLabel;
  }
}
