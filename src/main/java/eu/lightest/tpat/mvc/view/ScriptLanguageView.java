package eu.lightest.tpat.mvc.view;

import eu.lightest.tpat.mvc.controller.MainController;
import eu.lightest.tpat.mvc.controller.ScriptLanguagemControler;
import eu.lightest.tpat.mvc.model.ScriptLanguageModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ScriptLanguageView implements ILanguageView {
  @FXML
  Label mSaveLabel;

  @FXML
  Group mSaveGroup;

  @FXML
  TextArea mScriptArea;

  @FXML
  HBox mTitleBox;

  @FXML
  TitledPane mTitlePane;

  @FXML
  Button mRunQueryBtn;

  @FXML
  TextField mQueryField;

  @FXML
  Button mUndo;

  @FXML
  Button mRedo;


  private ScriptLanguagemControler mController = new ScriptLanguagemControler(this);

  @FXML
  public void initialize() {
    mUndo.setGraphic(new ImageView(new Image("images/USTUTT/32x32/undo.png", 16, 16, true, true)));
    mRedo.setGraphic(new ImageView(new Image("images/USTUTT/32x32/redo.png", 16, 16, true, true)));

    mTitleBox.prefWidthProperty().bind(mTitlePane.prefWidthProperty().subtract(16));
  }


  @FXML
  public void runQuery(Event e) {
    mController.testCode(mQueryField.getText());
  }

  public void initController(ScriptLanguageModel model, MainController mainController) {
    mController.init(model, mainController);
  }

  @Override
  public void undo() {
mController.undo();
  }

  @Override
  public void redo() {
mController.redo();
  }

  public TextArea getmScriptArea() {
    return mScriptArea;
  }

  public TextField getQueryField() {
    return mQueryField;
  }

  @Override
  public Pane getDetailPane() {
    return null;
  }

  public Label getSaveLabel() {
    return mSaveLabel;
  }

  public Group getSaveGroup() {
    return mSaveGroup;
  }
}
