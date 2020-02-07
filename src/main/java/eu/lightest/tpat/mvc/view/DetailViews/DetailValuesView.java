package eu.lightest.tpat.mvc.view.DetailViews;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailValuesController;
import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.AmountSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.DateSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.ExtractedSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.TextSubState;
import eu.lightest.tpat.utils.Parser.Parser;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.TreeItemModel;
import eu.lightest.tpat.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class DetailValuesView extends ADetailView {
  @FXML
  Accordion mAccordion;
  @FXML
  VBox mVBox;
  @FXML
  Tab mCustomTab;
  @FXML
  Tab mExtractTab;
  @FXML
  AnchorPane mValuesDetailView;
  @FXML
  TabPane mTabPane;
  @FXML
  TitledPane mAmountTitledPane;
  @FXML
  TextField mAmountCustomField;
  @FXML
  TitledPane mDateTitledPane;
  @FXML
  DatePicker mDateCustomPicker;
  @FXML
  TitledPane mTextTitledPane;
  @FXML
  TextField mTextCustomField;

  @FXML
  TreeView<TreeItemModel> mTreeView;

  private DetailValuesController mController = new DetailValuesController(this);

  @FXML
  public void initialize() {
    createTreeView();
    createCustomView();
  }

  @Override
  public void initController(Block assocBlock, IBaseController baseController) {
    mBlock = assocBlock;
    mController.init(assocBlock, baseController);
    setDomain();
    mController.restoreDetailView();
  }

  private void createTreeView() {
    mTreeView.setRoot(new TreeItem<>(new TreeItemModel("No domain", "", ""),
        new ImageView("images/USTUTT/24x24/Warnung3.png")));

    mTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.getValue().mType.isEmpty()) {
        mController.setChosenExtracted(newValue);
      }
    });
  }

  private void createCustomView() {
    setupAmountCustomField();
    setupDateCustomPicker();
    setupStringCustomField();

    mAccordion.expandedPaneProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        switch (newValue.getText()) {
          case "Amount":
            mController.setValueChoice(new AmountSubState());
            if (mAmountCustomField.getText() != null && !mAmountCustomField.getText().isEmpty())
              mController.setChosenAmount(mAmountCustomField.getText());
            break;
          case "Date":
            mController.setValueChoice(new DateSubState());
            dateCheck();
            break;
          case "Text":
            mController.setValueChoice(new TextSubState());
            if (mTextCustomField.getText() != null && !mTextCustomField.getText().isEmpty())
              mController.setChosenText(mTextCustomField.getText());
            break;
        }
      }
    });
  }

  public void restoreAmount(AmountSubState amountSubState) {
    mTabPane.getSelectionModel().select(mCustomTab);
    mAccordion.setExpandedPane(mAmountTitledPane);
    mAmountCustomField.setText(amountSubState.getValue());
    amountCheck();
  }

  public void restoreDate(DateSubState dateSubState) {
    mTabPane.getSelectionModel().select(mCustomTab);
    mAccordion.setExpandedPane(mDateTitledPane);
    mDateCustomPicker.setValue(dateSubState.getValue());
    dateCheck();
  }

  public void restoreText(TextSubState textSubState) {
    mTabPane.getSelectionModel().select(mCustomTab);
    mAccordion.setExpandedPane(mTextTitledPane);
    mTextCustomField.setText(textSubState.getValue());
    textCheck();
  }

  public void restoreExtracted(ExtractedSubState extractedSubState) {
    mTabPane.getSelectionModel().select(mExtractTab);
    TreeItem<TreeItemModel> treeItem = Utils.getTreeViewItem(mTreeView.getRoot(), extractedSubState.getValue());
    mTreeView.getSelectionModel().select(treeItem);

  }

  private void setDomain() {
    if (mBlock.getActualDragBlockController().getTemplateDomain() != null) {
      Parser.buildUiTree(mBlock.getActualDragBlockController().getTemplateDomain(), mTreeView);

      //TODO Is parser Choosen -> setup tree; make for common parser
      //TODO if not chosen make error message glow
    } else {
      //TODO clear tree
    }
  }

  private void setupAmountCustomField() {
    mAmountCustomField.setOnAction(event -> amountCheck());
    mAmountCustomField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      //on unfocus
      if (!newValue) {
        amountCheck();
      }
    });
  }

  private void amountCheck() {
    if (mAmountCustomField.getText() != null && mAmountCustomField.getText().matches("^([+-]?\\d*\\.?\\d*)$")) {
      mController.setChosenAmount(mAmountCustomField.getText());
    } else {
      mAmountCustomField.clear();
    }
  }

  private void setupStringCustomField() {
    mTextCustomField.setOnAction(event -> textCheck());
    mTextCustomField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      //on unfocus
      if (!newValue) {
        textCheck();
      }
    });
  }

  private void textCheck() {
    if (mTextCustomField.getText() != null && mTextCustomField.getText().matches("^[a-zA-Z0-9]+$")) {
      mController.setChosenText(mTextCustomField.getText());
    } else {
      mTextCustomField.clear();
    }
  }

  private void setupDateCustomPicker() {
    mDateCustomPicker.setOnAction(event -> dateCheck());
    mDateCustomPicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
      //on unfocus
      if (!newValue) {
        dateCheck();
      }
    });
  }

  private void dateCheck() {
    if (mDateCustomPicker.getValue() != null) {
      mController.setChosenDate(mDateCustomPicker.getValue());
    }
  }

  public DetailValuesController getController() {
    return mController;
  }


  public TextField getAmountCustomField() {
    return mAmountCustomField;
  }

  public DatePicker getDateCustomPicker() {
    return mDateCustomPicker;
  }

  public TextField getStringCustomField() {
    return mTextCustomField;
  }

  public TreeView<TreeItemModel> getTreeView() {
    return mTreeView;
  }
}
