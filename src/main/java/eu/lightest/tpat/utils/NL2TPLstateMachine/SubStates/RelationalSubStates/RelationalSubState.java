package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailRelationalsView;
import eu.lightest.tpat.utils.Exceptions.NonCompatibleBlock;
import eu.lightest.tpat.utils.Exceptions.NotEndLikeThisException;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.SubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.AValueSubState;
import javafx.scene.control.Toggle;

import java.util.Collections;

public abstract class RelationalSubState extends SubState {
  public abstract Toggle getToggle(DetailRelationalsView detailRelationalsView);

  @Override
  public boolean fullySpecified() {
    return true;
  }

  void relopSuccessor(State nextState) throws UserException {
    if (nextState == null) {
      throw new NotEndLikeThisException(getName(), Collections.singletonList("Value"));
    } else if (!(nextState instanceof AValueSubState)) {
      throw new NonCompatibleBlock(getName(), nextState.getName());
    }
  }
}
