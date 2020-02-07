package eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates;

import javafx.scene.paint.Color;

public class ExpressionState extends CategoryState {

  @Override
  public String translate() {
    return mBlockModel.getDetailModel().getSubState().translate();
  }
//  public String translate() {
//    return "Expression";
//  }

  @Override
  public String getLayout() {
    return "/layouts/detail_layouts/detail_expressions_layout.fxml";
  }

  @Override
  public Color getCategoryColor() {
    return new Color(0.0/255.0,82.0/255.0, 134.0/255.0, 255.0/255.0);
  }

  @Override
  public String getName() {
    return "Undefined Expression";
  }
}
