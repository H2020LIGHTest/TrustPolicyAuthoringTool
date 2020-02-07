package eu.lightest.tpat.mvc.view;

import eu.lightest.tpat.mvc.controller.MainController;
import eu.lightest.tpat.mvc.controller.NaturalLanguageController;
import eu.lightest.tpat.mvc.model.NaturalLanguageModel;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NaturalLanguageView implements ILanguageView {


  @FXML
  Group mSaveGroup;

  @FXML
  Label mSaveLabel;

  @FXML
  VBox mVBox;
  @FXML
  Button mUndo;
  @FXML
  Button mRedo;

  @FXML
  Button mExpressionBtn;
  @FXML
  Button mRelationalBtn;
  @FXML
  Button mTrustSchemeBtn;
  @FXML
  Button mDelegationBtn;
  @FXML
  Button mValueBtn;

  @FXML
  Pane mDetailPane;
  @FXML
  ScrollPane mScrollPane;

  @FXML
  SplitPane mSplitPane;
  private final NaturalLanguageController mController = new NaturalLanguageController(this);

  @FXML
  public void initialize() {
    mUndo.setGraphic(new ImageView(new Image("images/USTUTT/32x32/undo.png", 16, 16, true, true)));
    mRedo.setGraphic(new ImageView(new Image("images/USTUTT/32x32/redo.png", 16, 16, true, true)));
    // NEEDED: otherwise it will overflow and geting hidden without scroll function
    mSplitPane.prefHeightProperty().bind(mScrollPane.heightProperty());

  }

  @FXML
  public void onExpression() {
    mController.createDefaultBlock(new ExpressionState());
  }

  @FXML
  public void onRelational() {
    mController.createDefaultBlock(new RelationalState());
  }

  @FXML
  public void onTrustScheme() {
    mController.createDefaultBlock(new TrustSchemeState());
  }

  @FXML
  public void onValue() {
    mController.createDefaultBlock(new ValueState());
  }

  @FXML
  public void onDelegation() {
    mController.createDefaultBlock(new DelegationState());
  }

  public void initController(NaturalLanguageModel tpm, MainController mainController) {
    mController.init(tpm, mainController);
  }

  @FXML
  void onClickAddStatement() {
    mController.createStatement(mVBox, null);
  }

  @Override
  public void undo() {
    mController.undo();
  }

  @Override
  public void redo() {
    mController.redo();
  }

  @Override
  public Pane getDetailPane() {
    return mDetailPane;
  }

  public VBox getVBox() {
    return mVBox;
  }

  public Group getSaveGroup() {
    return mSaveGroup;
  }

  public Label getSaveLabel() {
    return mSaveLabel;
  }
}
