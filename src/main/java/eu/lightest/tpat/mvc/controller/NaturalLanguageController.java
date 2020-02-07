package eu.lightest.tpat.mvc.controller;

import eu.lightest.gtpl.tools.NL2TPLTools;
import eu.lightest.horn.util.AtvApiDummy;
import eu.lightest.tpat.mvc.controller.DetailControllers.DetailViewFactory;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import eu.lightest.tpat.mvc.view.ILanguageView;
import eu.lightest.tpat.mvc.view.NaturalStatementView;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.DragDrop.SnapBlockModel;
import eu.lightest.tpat.utils.SaveManager;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.CategoryState;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.ExpressionState;
import eu.lightest.horn.Interpreter;
import eu.lightest.horn.exceptions.HornFailedException;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import eu.lightest.tpat.mvc.model.NaturalLanguageModel;
import eu.lightest.tpat.mvc.model.NaturalStatementModel;
import eu.lightest.tpat.mvc.view.NaturalLanguageView;
import eu.lightest.tpat.utils.DragDrop.DragDropHandler;
import eu.lightest.tpat.utils.DragDrop.SnapBlock;
import org.apache.log4j.Logger;

import java.io.IOException;

import static eu.lightest.tpat.utils.SaveManager.playSaveAnimation;

public class NaturalLanguageController extends UsingBlockController implements IBaseController, IDetailViewListener {

  private NaturalLanguageModel mModel;
  private MainController mMainController;
  private final DragDropHandler mDragDropHandler = DragDropHandler.getInstance();
  private final Interpreter mGenerator = new Interpreter(new AtvApiDummy());

  private NaturalLanguageView mView;
  private DetailViewFactory mDetailViewFactory;

  private static Logger logger = Logger.getLogger(NaturalLanguageController.class);


  public NaturalLanguageController(NaturalLanguageView view) {
    this.mView = view;
    mDetailViewFactory = DetailViewFactory.getInstance();
  }

  public void init(NaturalLanguageModel naturalLanguageModel, MainController mainController) {
    mModel = naturalLanguageModel;
    mMainController = mainController;

    if (naturalLanguageModel.getNaturalStatementModels() != null &&
        !naturalLanguageModel.getNaturalStatementModels().isEmpty())
      restoreStatements();

    if (mModel.mFirstOpen) {
      NaturalStatementController controller = createStatement(mView.getVBox(), null);
      if (controller != null) {
        addFirstBlock(controller);
      }
      mModel.mFirstOpen = false;
    }
    DetailViewFactory.getInstance().resetDetailView(getView(), mDragDropHandler);
    refreshTPLandSave(false);
  }

  private void restoreStatements() {
    for (NaturalStatementModel statementModel : mModel.getNaturalStatementModels()) {
      createStatement(mView.getVBox(), statementModel);
    }
  }

  public NaturalStatementController createStatement(VBox vbox, NaturalStatementModel statementModel) {
    if (statementModel == null) {
      statementModel = new NaturalStatementModel(this.mModel);
      mModel.getNaturalStatementModels().add(statementModel);
      mModel.setActiveStatement(mModel.getNaturalStatementModels().indexOf(statementModel));
    }
    try {
      FXMLLoader loader = new FXMLLoader();
      TitledPane t = loader.load(getClass().getResource("/layouts/natural_statement_layout.fxml").openStream());
      vbox.getChildren().add(vbox.getChildren().size() - 1, t);
      NaturalStatementView statementView = loader.getController();
      return statementView.initController(this, statementModel, mDragDropHandler);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void addFirstBlock(NaturalStatementController controller) {
    Block block = controller.createNewBlock(new ExpressionState());
//    ((DetailExpressionsView)DetailViewFactory.sDetailView).mIf.fire(); //TODO
  }

  public void hideAllDropAreas() {
    for (NaturalStatementModel naturalStatementModel : mModel.getNaturalStatementModels()) {
      for (SnapBlockModel model : naturalStatementModel.getBlockList()) {
        model.getController().hideOwnDropAreas();
      }
    }
  }

  public void showAllDropAreas(SnapBlock draggedBlock) {
    for (NaturalStatementModel naturalStatementModel : mModel.getNaturalStatementModels()) {
      for (SnapBlockModel model : naturalStatementModel.getBlockList()) {
        if (model != draggedBlock.getSnapBlockModel())
          model.getController().showOwnDropAreas();
      }
    }
  }

  public Block createDefaultBlock(CategoryState state) {
    NaturalStatementController statementController = mModel.getNaturalStatementModels()
        .get(mModel.getActiveStatement()).getController();

    if (statementController != null) {
      return statementController.createNewBlock(state);
    }
    return null;
  }

  public NaturalLanguageView getView() {
    return mView;
  }

  @Override
  public void closeDetailView() {
    mDetailViewFactory.resetDetailView(mView, mDragDropHandler);
  }


  @Override
  public void onDetailViewSelected(Block assocBlock) {
    mDetailViewFactory.create(assocBlock);
  }

  @Override
  public void onDetailViewDeselected() {
    mDetailViewFactory.resetDetailView(mView, mDragDropHandler);
  }

  @Override
  public void onDragInProgress() {
    mDetailViewFactory.buildBinView(mView, mDragDropHandler);
  }

  @Override
  public void handleUserException(UserException ex) {
    //Show/handle users errors and warnings
    logger.debug("User targeted Message: " + ex.getMessage());
  }

  public NaturalLanguageModel getModel() {
    return mModel;
  }

  @Override
  public void refreshTPLandSave(boolean forHistory) {
    playSaveAnimation(mView.getSaveGroup(), mView.getSaveLabel());

    if (forHistory)
      super.addToHistory(mModel);

    try {
      SaveManager.saveModel(mModel);
      StringBuilder fullTPL = new StringBuilder();
      for (NaturalStatementModel statementModel : mModel.getNaturalStatementModels()) {
        fullTPL.append(statementModel.getController().refreshTPL()).append("\n");
      }
      logger.debug("Code revision says: " + mGenerator.runCodeRev(fullTPL.toString()));

      fullTPL.append(NL2TPLTools.addLibrary());
      SaveManager.saveTpl(mModel.mName, fullTPL.toString());

    } catch (HornFailedException | IOException e) {
      e.printStackTrace();
      //TODO
    }
  }

  @Override
  public ILanguageView getBaseView() {
    return this.mView;
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
      mMainController.createNatualView(model);
      mMainController.getMainModel().getTrustPolicyModels().set(
          mMainController.getMainModel().getTrustPolicyModels().indexOf(mModel), model);
      mMainController.getMainView().getTpListView().refresh();
      mModel = (NaturalLanguageModel) model;
    }
  }
}

