package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailRelationalsView;
import eu.lightest.tpat.utils.Exceptions.NonCompatibleBlock;
import eu.lightest.tpat.utils.Exceptions.NotEndLikeThisException;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.TrustSchemeSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.AValueSubState;
import javafx.scene.control.Toggle;

import java.util.Collections;

public class PartOfState extends RelationalSubState {

  @Override
  public void checkSuccessor(State nextState) throws UserException {
    if (nextState == null) {
      throw new NotEndLikeThisException(getName(), Collections.singletonList("Trustscheme"));
    } else if (!(nextState instanceof TrustSchemeSubState)) {
      throw new NonCompatibleBlock(getName(), nextState.getName());
    }
  }

  @Override
  public String translate() {
    return "is ";
  }

  @Override
  public String getName() {
    return "is part of";
  }

  @Override
  public Toggle getToggle(DetailRelationalsView view) {
    return  view.mPartOf;
  }
}
