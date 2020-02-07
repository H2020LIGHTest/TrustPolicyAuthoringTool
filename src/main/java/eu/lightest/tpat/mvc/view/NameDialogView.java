package eu.lightest.tpat.mvc.view;

import eu.lightest.tpat.mvc.controller.NameController;
import eu.lightest.tpat.mvc.model.MainModel;
import eu.lightest.tpat.utils.CustomStage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class NameDialogView {
  private final NameController mController = new NameController(this);

  @FXML
  Label mHeader;
  @FXML
  Label mText;

  @FXML
  TextField mNameField;
  @FXML
  Label mError;
  @FXML
  ImageView mErrorImg;


  @FXML
  public void initialize() {
    mHeader.setText("Give your Trust Policy a name.");
    mText.setText("Preferable a name with whom you associate the content.");
    resetError();

    Platform.runLater(() -> mNameField.requestFocus()); //get focus on text field
  }

  private void resetError() {
    mErrorImg.setImage(null);
    mError.setText("");
  }

  @FXML
  private void onOkay(){
    mController.okayAction();
  }

  @FXML
  private void onCancel(){
    mController.cancelAction();
  }

  public void initController(MainModel mainModel) {
    mController.init(mainModel);
  }

  public void onEnter(ActionEvent actionEvent) {
    mController.okayAction();
  }

  public String getNameFieldText() {
    return mNameField.getText();
  }

  public CustomStage getStage() {
    return (CustomStage) mHeader.getScene().getWindow();
  }

  public void setError(String msg) {
    mError.setText(msg);
    mErrorImg.setImage(new Image("images/USTUTT/24x24/x2.png"));
    this.getStage().sizeToScene();
  }
}
