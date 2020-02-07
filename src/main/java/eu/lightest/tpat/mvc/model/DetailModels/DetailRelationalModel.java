package eu.lightest.tpat.mvc.model.DetailModels;

import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.RelationalSubStates.RelationalSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.SubState;

import java.io.Serializable;

public class DetailRelationalModel implements IDetailModel, Serializable {
  public RelationalSubState mState;

  @Override
  public SubState getSubState() {
    return mState;
  }
}
