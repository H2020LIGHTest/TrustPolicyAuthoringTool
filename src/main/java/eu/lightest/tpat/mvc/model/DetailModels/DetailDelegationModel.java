package eu.lightest.tpat.mvc.model.DetailModels;

import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.DelegationSubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.SubState;
import eu.lightest.tpat.utils.Parser.Parser;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;

public class DetailDelegationModel implements IDetailModel, Serializable {
  public String mDelegationScheme;


  public List<String> getComboList() {
    return Parser.sTrustschemes;
  }

  @Override
  public SubState getSubState() {
    return new DelegationSubState(mDelegationScheme);
  }
}
