package eu.lightest.tpat.mvc.controller;

import eu.lightest.gtpl.tools.NL2TPLTools;
import eu.lightest.horn.Interpreter;
import eu.lightest.tpat.mvc.controller.DetailControllers.DetailViewFactory;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import eu.lightest.tpat.mvc.view.GraphicalLanguageView;
import eu.lightest.tpat.mvc.view.ILanguageView;
import eu.lightest.tpat.mvc.view.MainView;
import eu.lightest.tpat.utils.DragDrop.*;
import eu.lightest.tpat.utils.Exceptions.UserException;
import eu.lightest.tpat.utils.SaveManager;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import eu.lightest.tpat.mvc.model.GraphicalLanguageModel;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static eu.lightest.tpat.utils.SaveManager.playSaveAnimation;
import static java.util.Arrays.asList;

public class GraphicalLanguageController extends UsingBlockController implements DropSpaceController, IBaseController, IDetailViewListener {
  private GraphicalLanguageModel mModel;
  private MainController mMainController;
  private DragDropHandler mDragDropHandler;
  private GraphicalLanguageView mView;
  private DetailViewFactory mDetailViewFactory;

  private static Logger logger = Logger.getLogger(GraphicalLanguageController.class);

  public GraphicalLanguageController(GraphicalLanguageView graphicalLanguageView) {
    this.mView = graphicalLanguageView;
    mDragDropHandler = DragDropHandler.getInstance();
    mDetailViewFactory = DetailViewFactory.getInstance();
    mSpawnX = 0;
  }

  public void init(GraphicalLanguageModel graphicalLanguageModel, MainController mainController) {
    mModel = graphicalLanguageModel;
    this.mMainController = mainController;

    List<String> certificates = asList("PolicyDings", "PolicyDongs", "PolicyBlabla");
    //TODO new list

    // dropable groups
    mDragDropHandler.addDropHandling(mView.getPartOfPane(), this);
    mDragDropHandler.addDropHandling(mView.getEquivalentPane(), this);
    restoreBlocks();

    refreshTPLandSave(false);
  }

  @Override
  public void restoreBlocks() {
    for (GraphBlockModel blockModel : mModel.getListPartOf()) {
      GraphBlock graphBlock = BlockFactory.restoreGraphBlock(blockModel);
      mView.getPartOfPane().getChildren().add(graphBlock.getBlockView());
      blockModel.getGraphBlockController().addDragHandling(mDragDropHandler, this, this, this);
    }
    for (GraphBlockModel blockModel : mModel.getListEquivalent()) {
      GraphBlock graphBlock = BlockFactory.restoreGraphBlock(blockModel);
      mView.getEquivalentPane().getChildren().add(graphBlock.getBlockView());
      blockModel.getGraphBlockController().addDragHandling(mDragDropHandler, this, this, this);
    }
  }

  @Override
  public void addBlockToList(Block block, Pane pane) {
    if (pane == mView.getPartOfPane())
      mModel.getListPartOf().add(((GraphBlock) block).getGraphBlockModel());
    else
      mModel.getListEquivalent().add(((GraphBlock) block).getGraphBlockModel());
  }

  @Override
  public void removeCardFromList(Block block) {
    mModel.getListPartOf().remove(((GraphBlock) block).getGraphBlockModel());
    mModel.getListEquivalent().remove(((GraphBlock) block).getGraphBlockModel());
  }

  @Override
  public List<SnapBlockModel> getCards() {
    assert true;
    return null;
  }

  @Override
  public void onDropDone() {
    refreshTPLandSave(true);
  }

  @Override
  public String getTemplateDomain() {
    return null; //Not needed!
  }

  @Override
  public void refreshTPLandSave(boolean forHistory) {
    try {
      playSaveAnimation(mView.getSaveGroup(), mView.getSaveLabel());

      if (forHistory) {
        super.addToHistory(mModel);
        logger.debug("added to history");
      }

      StringBuilder fullTpl = new StringBuilder();
      List<GraphBlockModel> list = mModel.getListPartOf();
      for (GraphBlockModel blockModel : list) {
        State s = blockModel.getDetailModel().getSubState();
        if (s != null && s.fullySpecified()) {
          String clearText = "if input is format of generic ";
          clearText += "and certificate is " +
              blockModel.getDetailModel().getSubState().translate();
          clearText += "then accept it;";
          logger.debug("CLEAR TEXT START\n" + clearText + "\nCLEAR TEXT END");
          String tpl = NL2TPLTools.translateToTPLinternally(clearText);
          fullTpl.append(tpl).append("\n");
        }
      }

      list = mModel.getListEquivalent();
      for (GraphBlockModel graphBlockModel : list) {
        State s = graphBlockModel.getDetailModel().getSubState();
        if (s != null && s.fullySpecified()) {
          String clearText = "if input is format of generic ";
          clearText += "and certificate is equivalent to " +
              graphBlockModel.getDetailModel().getSubState().translate();
          clearText += "then accept it;";
          logger.debug("CLEAR TEXT START\n" + clearText + "\nCLEAR TEXT END");
          String tpl = NL2TPLTools.translateToTPLinternally(clearText);
          fullTpl.append(tpl).append("\n");
        }
      }

      fullTpl.append(NL2TPLTools.addLibrary());
      logger.debug("FULL TPL START\n " + fullTpl + "\nFULL TPL END");
      SaveManager.saveTpl(mModel.mName, fullTpl.toString());

      SaveManager.saveModel(mModel);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addToParfOf() {
    addTo( mView.getPartOfPane());
  }

  public void addToEquiv() {
    addTo(mView.getEquivalentPane());
  }

  private void addTo(AnchorPane targetPane) {
    GraphBlock block = BlockFactory.newGraphBlock(this, targetPane);
    block.addDragHandling(mDragDropHandler, this, this, this);

    targetPane.getChildren().add(block.getBlockView());
    targetPane.requestFocus();
    positionNewBlock(block.getBlockView());

    DetailViewFactory.getInstance().create(block);
    refreshTPLandSave(true);
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
  public void closeDetailView() {
    mDetailViewFactory.resetDetailView(mView, mDragDropHandler);
  }

  @Override
  public void handleUserException(UserException ex) {
    //TODO show users errors or warnings
    logger.debug("Exception: " + ex.getMessage());
  }


  @Override
  public ILanguageView getBaseView() {
    return this.mView;
  }

  public void redo() {
    try {
      TrustPolicyModel model = super.redo(mModel);
      updateModel(model);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void undo() {
    try {
      TrustPolicyModel model = super.undo(mModel);
      updateModel(model);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void updateModel(TrustPolicyModel model) throws IOException {
    if (model != null) {
      mMainController.createGraphicalView(model);
      mMainController.getMainModel().getTrustPolicyModels().set(
          mMainController.getMainModel().getTrustPolicyModels().indexOf(mModel), model);
      mMainController.getMainView().getTpListView().refresh();
      mModel = (GraphicalLanguageModel) model;
    }
  }

  @Override
  void positionNewBlock(BlockView blockView) {
    mSpawnX = (mSpawnX + 25) % 50;
    mSpawnY = (mSpawnY + 16 + (int)blockView.getHeight()) %
        ((int) MainView.sScene.getWindow().getHeight()- 4 * (int)blockView.getHeight());
    blockView.relocate(mSpawnX, mSpawnY);
  }
}