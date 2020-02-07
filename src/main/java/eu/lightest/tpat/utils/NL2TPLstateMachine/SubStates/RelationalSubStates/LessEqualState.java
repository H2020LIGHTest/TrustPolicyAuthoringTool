package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailRelationalsView;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import javafx.scene.control.Toggle;

public class LessEqualState extends RelationalSubState {
  @Override
  public void checkSuccessor(State nextState) throws UserException {
    relopSuccessor(nextState);
  }

  @Override
  public String translate() {
    return getName() + " ";
  }

  @Override
  public String getName() {
    return "less than or equal to";
  }

  @Override
  public Toggle getToggle(DetailRelationalsView view) {
    return view.mLessEqual;
  }
}
