package eu.lightest.tpat.mvc.view;

import eu.lightest.tpat.mvc.controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import eu.lightest.tpat.mvc.controller.GraphicalLanguageController;
import eu.lightest.tpat.mvc.model.GraphicalLanguageModel;
import javafx.scene.layout.Pane;


public class GraphicalLanguageView implements ILanguageView{

  private final GraphicalLanguageController mGraphicalLanguageController = new GraphicalLanguageController(this);
  public Label mSaveLabel;

  public Group mSaveGroup;

  public SplitPane getTwoColumns() {
    return mTwoColumns;
  }

  @FXML
  SplitPane mTwoColumns;

  @FXML
  AnchorPane mPartOfPane;

  @FXML
  AnchorPane mEquivalentPane;

  @FXML
  Pane mDetailPane;

  @FXML
  Button mUndo;

  @FXML
  Button mRedo;

  @FXML
  public void initialize() {
    mUndo.setGraphic(new ImageView(new Image("images/USTUTT/32x32/undo.png", 16, 16, true, true)));
    mRedo.setGraphic(new ImageView(new Image("images/USTUTT/32x32/redo.png", 16, 16, true, true)));
  }


  @FXML
  public void onPartOf() {
    mGraphicalLanguageController.addToParfOf();
  }
  @FXML
  public void onEquiv() {
    mGraphicalLanguageController.addToEquiv();
  }

  public void initController(GraphicalLanguageModel tpm, MainController mainController) {
    mGraphicalLanguageController.init(tpm, mainController);
  }

  @Override
  public Pane getDetailPane() {
    return mDetailPane;
  }

  @Override
  public void undo() {
    mGraphicalLanguageController.undo();

  }

  @Override
  public void redo() {
    mGraphicalLanguageController.redo();
  }

  public AnchorPane getPartOfPane() {
    return mPartOfPane;
  }

  public AnchorPane getEquivalentPane() {
    return mEquivalentPane;
  }

  public Label getSaveLabel() {
    return mSaveLabel;
  }

  public Group getSaveGroup() {
    return mSaveGroup;
  }
}
