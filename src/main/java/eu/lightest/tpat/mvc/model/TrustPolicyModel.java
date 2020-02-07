package eu.lightest.tpat.mvc.model;

import eu.lightest.gtpl.datatype.TrustPolicy;

public abstract class TrustPolicyModel implements java.io.Serializable {
  public TrustPolicyModel previousModelState = null;
  public TrustPolicyModel nextModelState = null;

  public enum TplType {
    graphical,
    natural,
    script;
  }

  public String mName;
  public TplType mType;

  public TrustPolicyModel(String name, TplType type) {
    this.mName = name;
    this.mType = type;
  }

  public void addToHistory(TrustPolicyModel copy) {
    this.previousModelState = copy;
    this.previousModelState.nextModelState = this;

//    TrustPolicyModel iter = this;
//    int undoDepth = 10;
//    while (iter.previousModelState.previousModelState != null) {
//      undoDepth--;
//      iter = iter.previousModelState;
//      if (undoDepth == 0) {
//        iter.previousModelState.nextModelState = null;
//        iter.previousModelState = null;
//        break;
//      }
//    }
  }
}
