package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailValuesView;
import eu.lightest.tpat.utils.Exceptions.NonCompatibleBlock;
import eu.lightest.tpat.utils.Exceptions.NotEndLikeThisException;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.AndState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.ThenState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates.RelationalSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.SubState;
import eu.lightest.tpat.utils.Utils;
import java.util.Arrays;

public abstract class AValueSubState extends SubState {

  @Override
  public void checkSuccessor(State nextState) throws UserException {
    if (nextState == null) {
      throw new NotEndLikeThisException(getName(), Arrays.asList("Arithmetic Expression", "Relational Sign", "'Part of'", "'Equivalent to'", "Delegation"));
    } else if (!(nextState instanceof ThenState || nextState instanceof AndState ||
        Utils.isArith(nextState) || nextState instanceof RelationalSubState)) {
      throw new NonCompatibleBlock(getName(), nextState.getName());
    }
  }

  public abstract void callCorrespondingDetailView(DetailValuesView mView);
}
