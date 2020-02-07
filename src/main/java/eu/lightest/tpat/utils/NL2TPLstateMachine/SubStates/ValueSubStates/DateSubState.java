package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailValuesView;

import java.time.LocalDate;

public class DateSubState extends AValueSubState {
  private LocalDate mValue;

  @Override
  public String translate() {
    return mValue.toString() + " ";
  }

  @Override
  public String getName() {
    if (fullySpecified())
      return mValue.toString();
    else
      return "Unspecified Value";
  }

  @Override
  public boolean fullySpecified() {
    return mValue != null;
  }

  @Override
  public void callCorrespondingDetailView(DetailValuesView view) {
    view.restoreDate(this);
  }

  public void setValue(LocalDate value) {
    this.mValue = value;
  }

  public LocalDate getValue() {
    return mValue;
  }
}
