package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailExpressionsView;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import javafx.scene.control.Toggle;

public class MultipState extends ExpressionalSubState {
  @Override
  public void checkSuccessor(State nextState) throws UserException {
    arithSuccessor(nextState);
  }

  @Override
  public Toggle getToggle(DetailExpressionsView view) {
    return view.mMulti;
  }

  @Override
  public String translate() {
    return "* ";
  }

  @Override
  public String getName() {
    return "multiply";
  }
}
