package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailExpressionsView;
import eu.lightest.tpat.utils.Exceptions.NonCompatibleBlock;
import eu.lightest.tpat.utils.Exceptions.NotEndLikeThisException;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.DelegationSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.AValueSubState;
import javafx.scene.control.Toggle;

import java.util.Collections;

public class AndState extends ExpressionalSubState {
  @Override
  public void checkSuccessor(State nextState) throws UserException {
    if (nextState == null) {
      throw new NotEndLikeThisException(getName(), Collections.singletonList("Value"));
    } else if (!(nextState instanceof AValueSubState || nextState instanceof DelegationSubState)) {
      throw new NonCompatibleBlock(getName(), nextState.getName());
    }
  }

  @Override
  public String translate() {
    return getName() + " ";
  }

  @Override
  public String getName() {
    return "and";
  }

  @Override
  public Toggle getToggle(DetailExpressionsView view) {
    return view.mAnd;
  }
}
