package eu.lightest.tpat.mvc.model.DetailModels;

import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.ExpressionalSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.SubState;

import java.io.Serializable;

public class DetailExpressionModel implements IDetailModel, Serializable {
  public ExpressionalSubState mState;

  @Override
  public SubState getSubState() {
    return mState;
  }
}
