package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailRelationalsView;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import javafx.scene.control.Toggle;

public class EqualState extends RelationalSubState {
  @Override
  public void checkSuccessor(State nextState) throws UserException {
    relopSuccessor(nextState);
  }

  @Override
  public String translate() {
    return "equals ";
  }

  @Override
  public String getName() {
    return "equals";
  }

  @Override
  public Toggle getToggle(DetailRelationalsView view) {
    return view.mEqual;
  }
}
