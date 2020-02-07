package eu.lightest.tpat.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class MainModel {
  private List<TrustPolicyModel> mTrustPolicyModels = new ArrayList<>();

  public List<TrustPolicyModel> getTrustPolicyModels() {
    return mTrustPolicyModels;
  }
}
