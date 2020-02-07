package eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates;

import javafx.scene.paint.Color;

public class DelegationState extends CategoryState {

  @Override
  public String translate() {
    return mBlockModel.getDetailModel().getSubState().translate();
  }
//  public String translate() {
//    return "Trustscheme";
//  }

  @Override
  public String getLayout() {
    return "/layouts/detail_layouts/detail_delegation_layout.fxml";
  }

  @Override
  public Color getCategoryColor() {
    return new Color(76.0/255.0, 103.0/255.0, 110.0/255.0, 255.0/255.0);
  }

  @Override
  public String getName() {
    return "Undefined Delegation";
  }
}
