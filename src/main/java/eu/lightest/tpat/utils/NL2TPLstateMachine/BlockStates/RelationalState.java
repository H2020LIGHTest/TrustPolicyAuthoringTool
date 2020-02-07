package eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates;

import javafx.scene.paint.Color;

public class RelationalState extends CategoryState {

  @Override
  public String translate() {
    return mBlockModel.getDetailModel().getSubState().translate();
  }
//  public String translate() {
//    return "Relational";
//  }

  @Override
  public String getLayout() {
    return "/layouts/detail_layouts/detail_relationals_layout.fxml";
  }

  @Override
  public Color getCategoryColor() {
    return new Color(0.0/255.0, 120.0/255.0, 133.0/255.0, 255.0/255.0);
  }

  @Override
  public String getName() {
    return "Undefined Relation";
  }
}
