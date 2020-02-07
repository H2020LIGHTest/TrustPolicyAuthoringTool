package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.mvc.model.MainModel;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import eu.lightest.tpat.mvc.view.ChooseDialogView;
import eu.lightest.tpat.utils.CustomStage;

public class ChooseDialogController {
  private final ChooseDialogView mView;
  private MainModel mMainModel;

  public ChooseDialogController(ChooseDialogView chooseDialogView) {
    this.mView = chooseDialogView;
  }

  public void doBtnAction(TrustPolicyModel.TplType style) {
    CustomStage stage = (CustomStage) mView.getHeader().getScene().getWindow();
    stage.setStyle(style);
    stage.close();
  }

  public void init(MainModel mainModel) {
    mMainModel = mainModel;
  }

  public void cancelAction() {
    CustomStage stage = (CustomStage) mView.getHeader().getScene().getWindow();
    stage.close();
  }
}
