package eu.lightest.tpat.mvc.view;

import eu.lightest.tpat.mvc.controller.NaturalLanguageController;
import eu.lightest.tpat.utils.CustomComponents.ResizeManager;
import eu.lightest.tpat.utils.Parser.Parser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import eu.lightest.tpat.mvc.controller.NaturalStatementController;
import eu.lightest.tpat.mvc.model.NaturalStatementModel;
import eu.lightest.tpat.utils.DragDrop.DragDropHandler;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class NaturalStatementView {
  @FXML
  ImageView mScalingImg;
  @FXML
  ImageView mErrorImg;
  @FXML
  Label mErrorLabel;

  @FXML
  TitledPane mTitledPane;

  @FXML
  AnchorPane mContentPane;

  private NaturalStatementController mController = new NaturalStatementController(this);

  private ComboBox<String> mDomainSelection;

  private Label mChoiceLabel;
  @FXML
  public void initialize() {
    mDomainSelection = new ComboBox<>(FXCollections.observableList(new ArrayList<>(Parser.sDomainList.keySet())));
    mDomainSelection.setPromptText("Choose a domain");

    mChoiceLabel = new Label("Format: ");
    mChoiceLabel.setMaxHeight(Double.MAX_VALUE);//for centering

    HBox hbox = new HBox(mChoiceLabel, mDomainSelection);
    hbox.setSpacing(8);
    mTitledPane.setGraphic(hbox);

    mErrorLabel.managedProperty().bind(mErrorLabel.visibleProperty());
    mErrorLabel.maxWidthProperty().bind(mTitledPane.widthProperty()
        .subtract(mErrorImg.fitWidthProperty().multiply(4)));
    mErrorLabel.setVisible(false);
    mErrorImg.setImage(new Image("images/USTUTT/24x24/Warnung3.png"));

    mScalingImg.setImage(new Image("images/dragLarge.png", 24,24,true,true));
  }

  public NaturalStatementController initController(NaturalLanguageController parentController, NaturalStatementModel statementModel, DragDropHandler dragDropHandler) {
    mController.init(parentController, statementModel, dragDropHandler);
    ResizeManager.makeResizable(mTitledPane);
    mTitledPane.setMinHeight(mController.getHeight());
    mTitledPane.minHeightProperty().addListener((observable, oldValue, newValue) -> {
      mController.setHeight(newValue);
    });
    return mController;
  }

  public AnchorPane getContentPane() {
    return mContentPane;
  }

  public ComboBox<String> getDomainComboBox() {
    return mDomainSelection;
  }

  public TitledPane getTitledPane() {
    return mTitledPane;
  }

  public void setError(String message) {
    mErrorLabel.setText(message);
    mErrorLabel.setVisible(true);
    mErrorLabel.setTextFill(new Color(1,0,0,1));

    mErrorImg.setImage(new Image("/images/USTUTT/24x24/x2.png",24,24,true, true));
  }

  public void setWarning(String message) {
    mErrorLabel.setText(message);
    mErrorLabel.setVisible(true);
    mErrorLabel.setTextFill(new Color(0,0,0,1));
    mErrorImg.setImage(new Image("/images/USTUTT/24x24/Warnung3.png",24,24,true, true));
  }

  public void setNoError() {
    mErrorLabel.setVisible(false);
    mErrorImg.setImage(new Image("/images/USTUTT/24x24/haken2.png",24,24,true, true));
  }
}
