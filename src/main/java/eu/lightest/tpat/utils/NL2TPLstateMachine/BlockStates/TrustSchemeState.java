package eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates;

import javafx.scene.paint.Color;

public class TrustSchemeState extends CategoryState {

  @Override
  public String translate() {
    return mBlockModel.getDetailModel().getSubState().translate();
  }
//  public String translate() {
//    return "Trustscheme";
//  }

  @Override
  public String getLayout() {
    return "/layouts/detail_layouts/detail_trust_schemes_layout.fxml";
  }

  @Override
  public Color getCategoryColor() {
    return new Color(0.0/255.0, 145.0/255.0, 100.0/255.0, 255.0/255.0);
  }

  @Override
  public String getName() {
    return "Undefined Trustscheme";
  }
}
