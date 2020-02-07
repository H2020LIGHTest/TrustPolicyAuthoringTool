package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.mvc.model.MainModel;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import eu.lightest.tpat.mvc.view.NameDialogView;
import eu.lightest.tpat.utils.CustomStage;
import eu.lightest.tpat.utils.DialogException;

public class NameController {
  private NameDialogView mView;
  private MainModel mMainModel;

  public NameController(NameDialogView view) {
    mView = view;
  }

  public void init(MainModel mainModel) {
    mMainModel = mainModel;
  }

  public String checkName(String text) throws DialogException {
    if (text.matches("^[a-zA-Z0-9]+$")) {
      for (TrustPolicyModel tpm : mMainModel.getTrustPolicyModels()) {
        if (tpm.mName.equals(text)) {
          throw new DialogException("Name already taken");
        }
      }
      return text;
    } else
      throw new DialogException("Please retry entering a valid name\n" +
          "• one cohesive name\n" +
          "• only alphanumeric characters");
  }

  public void okayAction() {
    try {
      CustomStage stage = mView.getStage();
      stage.setName(checkName(mView.getNameFieldText()));
      stage.close();
    } catch (DialogException e) {
      mView.setError(e.getMessage());
    }
  }

  public void cancelAction() {
    CustomStage stage = mView.getStage();
    stage.setName(null);
    stage.close();
  }
}
