package eu.lightest.tpat.mvc.model;

public class ScriptLanguageModel extends TrustPolicyModel {

  private String mScript = "";

  public ScriptLanguageModel(String name, TplType tplType) {
    super(name, tplType);
  }

  public String getScript() {
    return mScript;
  }

  public void setScript(String mScript) {
    this.mScript = mScript;
  }
}
