package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailValuesView;

public class TextSubState extends AValueSubState {
  private String mValue;

  @Override
  public String translate() {
    return mValue + " ";
  }

  @Override
  public String getName() {
    if (fullySpecified())
      return mValue;
    else
      return "Unspecified Value";
  }

  @Override
  public boolean fullySpecified() {
    return mValue != null && !mValue.isEmpty();
  }

  @Override
  public void callCorrespondingDetailView(DetailValuesView view) {
    view.restoreText(this);
  }

  public void setValue(String value) {
    this.mValue = value;
  }

  public String getValue() {
    return mValue;
  }
}
