package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates;

import eu.lightest.tpat.utils.Exceptions.NonCompatibleBlock;
import eu.lightest.tpat.utils.Exceptions.NotEndLikeThisException;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.AndState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.ThenState;

import java.util.Arrays;
import java.util.Collections;

public class DelegationSubState extends SubState {

  private String mName;

  @Override
  public void checkSuccessor(State nextState) throws UserException {
    if (nextState == null) {
      throw new NotEndLikeThisException(getName(), Arrays.asList("And", "Then"));
    } else if (!(nextState instanceof ThenState || nextState instanceof AndState)) {
      throw new NonCompatibleBlock(getName(), nextState.getName());
    }
  }

  public DelegationSubState(String name){
    this.mName = name;
  }

  @Override
  public String translate() {
    return "delegation is from " + mName.replace(" ", "") + " ";
  }

  @Override
  public String getName() {
    return "has delegation in " + mName;
  }

  @Override
  public boolean fullySpecified() {
    return mName != null && !mName.isEmpty();
  }
}
