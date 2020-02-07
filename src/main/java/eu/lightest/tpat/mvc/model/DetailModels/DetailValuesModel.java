package eu.lightest.tpat.mvc.model.DetailModels;

import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates.AValueSubState;

import java.io.Serializable;

public class DetailValuesModel implements IDetailModel, Serializable {

  private AValueSubState mValueSubState;

  @Override
  public AValueSubState getSubState() {
    return mValueSubState;
  }

  public void setValueSubState(AValueSubState mValueSubState) {
    this.mValueSubState = mValueSubState;
  }
}
