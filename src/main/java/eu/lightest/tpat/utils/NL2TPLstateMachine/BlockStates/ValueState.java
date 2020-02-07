package eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates;

import javafx.scene.paint.Color;

public class ValueState extends CategoryState {

  @Override
  public String translate() {
    return mBlockModel.getDetailModel().getSubState().translate();
  }
//  public String translate() {
//    return "Value";
//  }

  @Override
  public String getLayout() {
    return "/layouts/detail_layouts/detail_values_layout.fxml";
  }

  @Override
  public Color getCategoryColor() {
    return new Color(73.0/255.0, 94.0/255.0, 112.0/255.0, 255.0/255.0);
  }

  @Override
  public String getName() {
    return "Undefined Value";
  }
}
