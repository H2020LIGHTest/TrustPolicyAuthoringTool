package eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ValueSubStates;

import eu.lightest.tpat.mvc.view.DetailViews.DetailValuesView;
import eu.lightest.tpat.utils.TreeItemModel;
import javafx.scene.control.TreeItem;

public class ExtractedSubState extends AValueSubState {
  private TreeItemModel mTreeItemModel;

  @Override
  public String translate() {
    return mTreeItemModel.mPath  + " ";
  }

  @Override
  public String getName() {
    if (fullySpecified())
      return mTreeItemModel.mName;
    else
      return "Unspecified Value";
  }

  @Override
  public boolean fullySpecified() {
    return mTreeItemModel != null && !mTreeItemModel.mName.isEmpty();
  }

  @Override
  public void callCorrespondingDetailView(DetailValuesView view) {
    view.restoreExtracted(this);
  }

  public void setValue(TreeItemModel value) {
    this.mTreeItemModel = value;
  }

  public TreeItemModel getValue() {
    return mTreeItemModel;
  }
}
