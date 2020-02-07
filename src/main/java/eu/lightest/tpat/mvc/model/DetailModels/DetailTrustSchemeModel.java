package eu.lightest.tpat.mvc.model.DetailModels;

import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.SubState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.TrustSchemeSubState;
import eu.lightest.tpat.utils.Parser.Parser;

import java.io.Serializable;
import java.util.List;
import static java.util.Arrays.asList;

public class DetailTrustSchemeModel implements IDetailModel, Serializable {
  public String mTrustScheme;

  public List<String> getComboList() {
    return Parser.sTrustschemes;
  }

  @Override
  public SubState getSubState() {
    return new TrustSchemeSubState(mTrustScheme);
  }
}
