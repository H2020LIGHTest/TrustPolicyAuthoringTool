package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailExpressionsView;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import javafx.scene.control.Toggle;

public class ThenState extends ExpressionalSubState {
  @Override
  public void checkSuccessor(State nextState) {

  }

  @Override
  public String translate() {
    return getName()  + "; ";
  }

  @Override
  public String getName() {
    return "then accept it";
  }

  @Override
  public Toggle getToggle(DetailExpressionsView view) {
    return view.mThen;
  }
}
