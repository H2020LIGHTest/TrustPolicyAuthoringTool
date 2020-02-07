package eu.lightest.tpat.mvc.view.DetailViews;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailExpressionsController;
import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.*;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.AndState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.IfState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.ThenState;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DetailExpressionsView extends ADetailView {


  @FXML
  public RadioButton mIf;
  @FXML
  public RadioButton mThen;
  @FXML
  public RadioButton mAnd;
  @FXML
  public RadioButton mPlus;
  @FXML
  public RadioButton mMinus;
  @FXML
  public RadioButton mMulti;
  @FXML
  public RadioButton mDiv;


  private ToggleGroup mToggleGroup = new ToggleGroup();
  private DetailExpressionsController mController = new DetailExpressionsController(this);


  @FXML
  public void initialize() {
    setUpRadioBtn(mIf, new IfState());
    setUpRadioBtn(mThen, new ThenState());
    setUpRadioBtn(mAnd, new AndState());
    setUpRadioBtn(mPlus, new PlusState());
    setUpRadioBtn(mMinus, new MinusState());
    setUpRadioBtn(mMulti, new MultipState());
    setUpRadioBtn(mDiv, new DivState());
  }

  @Override
  public void initController(Block assocBlock, IBaseController baseController) {
    mController.init(assocBlock, baseController);
//    if(!assocBlock.getHeaderText().equals("undefined"))
      mController.restoreDetailView();
  }

  private void setUpRadioBtn(RadioButton specific, ExpressionalSubState state) {
    specific.setToggleGroup(mToggleGroup);
    specific.setOnMouseClicked(event -> mController.setChoice(state));
  }

  public void setRadioGroup(ExpressionalSubState state) {
        mToggleGroup.selectToggle(state.getToggle(this));
  }
}
