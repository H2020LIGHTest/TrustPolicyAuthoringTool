package eu.lightest.tpat.mvc.controller;

import eu.lightest.horn.util.AtvApiDummy;
import eu.lightest.tpat.mvc.model.ScriptLanguageModel;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import eu.lightest.tpat.mvc.view.ILanguageView;
import eu.lightest.tpat.mvc.view.ScriptLanguageView;
import eu.lightest.tpat.utils.AlertDialog;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.SaveManager;
import eu.lightest.horn.Interpreter;
import eu.lightest.horn.exceptions.HornFailedException;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import eu.lightest.tpat.utils.ReschedulableTimer;

import java.io.IOException;
import java.util.regex.Pattern;

public class ScriptLanguagemControler extends UndoRedoController implements IBaseController {

  private final ScriptLanguageView mView;
  private ScriptLanguageModel mModel;
  private MainController mMainController;
  private Interpreter mGenerator = new Interpreter(new AtvApiDummy());
  private final Tooltip mToolTip = new Tooltip("Every thing is fine.");
  private ReschedulableTimer mDelayedCheck;


  public ScriptLanguagemControler(ScriptLanguageView scriptLanguageView) {
    mView = scriptLanguageView;

    mDelayedCheck = new ReschedulableTimer(() -> {
      try {
        SaveManager.playSaveAnimation(mView.getSaveGroup(), mView.getSaveLabel());
        SaveManager.saveModel(mModel);
        mGenerator.runCodeRev(mView.getmScriptArea().getText());

        Platform.runLater(() -> {
          mView.getmScriptArea().setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
          mToolTip.setText("Everything is fine.");
        });

        SaveManager.saveTpl(mModel.mName, mModel.getScript());

      } catch (HornFailedException e) {
        Platform.runLater(() -> {
          mView.getmScriptArea().setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
          mToolTip.setText(e.getMessage());
        });
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  public void init(ScriptLanguageModel model, MainController mainController) {
    mModel = model;
    mMainController = mainController;
    mView.getmScriptArea().setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
    mView.getmScriptArea().setTooltip(mToolTip);

    if (mModel.getScript() != null && !mModel.getScript().isEmpty())
      restoreScript();


    mView.getmScriptArea().textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        refreshTPLandSave(true);
        mModel.setScript(mView.getmScriptArea().getText());
      }
    });

    refreshTPLandSave(false);
  }

  private void restoreScript() {
    mView.getmScriptArea().setText(mModel.getScript());
  }

  public void testCode(String query) {
    String tplCode = mView.getmScriptArea().getText();
    if (tplCode.isEmpty()) {
      new AlertDialog("Result", "The Tpl code is not valid", "Please reconsider your Tpl Code", AlertDialog.Type.warning);
    } else if (query.isEmpty()) {
      new AlertDialog("Result", "The Tpl query is not valid", "Please reconsider your Tpl Query", AlertDialog.Type.warning);
    } else {
      try {
        String rootVariable = Pattern.compile(".*?[(](.*?)[)].*?").matcher(query).group(1);
        mGenerator.runInternal(query, rootVariable, tplCode);
        new AlertDialog("Result", "Query yield positive success.", "Your TPL policy would accept this query", AlertDialog.Type.info);
      } catch (IllegalStateException e) {
        new AlertDialog("Result", "No valid query", "Check your query", AlertDialog.Type.error);
      } catch (HornFailedException e) {
        new AlertDialog("Result", "Query yield negative success.", e.getMessage(), AlertDialog.Type.error);
      }
    }
  }


  @Override
  public void refreshTPLandSave(boolean forHistory) {
    mDelayedCheck.schedule(1000);
    if (forHistory)
      super.addToHistory(mModel);
  }

  @Override
  public ILanguageView getBaseView() {
    return mView;
  }

  @Override
  public void closeDetailView() {
  }

  @Override
  public void handleUserException(UserException ex) {
//TODO
  }

  public void undo() {
    try {
      TrustPolicyModel model = super.undo(mModel);
      if (model != null) {
        updateModel(model);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void redo() {
    try {
      TrustPolicyModel model = super.redo(mModel);
      if (model != null) {
        updateModel(model);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void updateModel(TrustPolicyModel model) throws IOException {
    if (model != null) {
      mMainController.createScriptView(model);
      mMainController.getMainModel().getTrustPolicyModels().set(
          mMainController.getMainModel().getTrustPolicyModels().indexOf(mModel), model);
      mMainController.getMainView().getTpListView().refresh();
      mModel = (ScriptLanguageModel) model;
    }
  }
}
