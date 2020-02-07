package eu.lightest.tpat.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class NaturalLanguageModel extends TrustPolicyModel{

  private List<NaturalStatementModel> mNaturalStatementModels = new ArrayList<>();

  private int mActiveStatement;
  public boolean mFirstOpen = true;

  // Used for Reloading saves only
//  private static final long serialVersionUID = 520067334508333005L;
  public NaturalLanguageModel(String name, TplType type, List<NaturalStatementModel> mNaturalStatementModels, int mActiveStatement, boolean mFirstOpen) {
    super(name, type);
    this.mNaturalStatementModels = mNaturalStatementModels;
    this.mActiveStatement = mActiveStatement;
    this.mFirstOpen = mFirstOpen;
  }

  public NaturalLanguageModel(String name, TplType tplType) {
    super(name, tplType);
  }

  public List<NaturalStatementModel> getNaturalStatementModels() {
    return mNaturalStatementModels;
  }

  public void setActiveStatement(int activeStatementIndex){
    this.mActiveStatement = activeStatementIndex;
  }

  public int getActiveStatement() {
    if (mActiveStatement < mNaturalStatementModels.size())
      return mActiveStatement;
    else
      return mNaturalStatementModels.size();
  }
}
