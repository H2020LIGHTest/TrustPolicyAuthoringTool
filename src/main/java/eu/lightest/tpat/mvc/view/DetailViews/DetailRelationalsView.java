package eu.lightest.tpat.mvc.view.DetailViews;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailRelationalController;
import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates.EquivalentState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates.*;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DetailRelationalsView extends ADetailView {

  @FXML
  public RadioButton mLess;
  @FXML
  public RadioButton mGreater;
  @FXML
  public RadioButton mLessEqual;
  @FXML
  public RadioButton mGreaterEqual;
  @FXML
  public RadioButton mEqual;
  @FXML
  public RadioButton mEquiv;
  @FXML
  public RadioButton mPartOf;

  private DetailRelationalController mController = new DetailRelationalController(this);
  private ToggleGroup mToggleGroup = new ToggleGroup();

  @FXML
  public void initialize() {
    setUpRadioBtn(mLess, new LessState());
    setUpRadioBtn(mGreater, new GreaterState());
    setUpRadioBtn(mLessEqual, new LessEqualState());
    setUpRadioBtn(mGreaterEqual, new GreaterEqualState());
    setUpRadioBtn(mEqual, new EqualState());
    setUpRadioBtn(mEquiv, new EquivalentState());
    setUpRadioBtn(mPartOf , new PartOfState());

  }

  @Override
  public void initController(Block assocBlock, IBaseController baseController) {
    mController.init(assocBlock, baseController);
//    if (!assocBlock.getHeaderText().equals("undefined"))
      mController.restoreDetailView();
  }

  private void setUpRadioBtn(RadioButton specific, RelationalSubState state) {
    specific.setToggleGroup(mToggleGroup);
    specific.setOnMouseClicked(event -> mController.setChoice(state));
  }


  public void setRadioGroup(RelationalSubState state) {
    mToggleGroup.selectToggle(state.getToggle(this));
  }
}
