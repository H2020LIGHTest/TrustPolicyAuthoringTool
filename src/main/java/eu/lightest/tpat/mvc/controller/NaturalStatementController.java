package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailViewFactory;
import eu.lightest.tpat.mvc.view.NaturalStatementView;
import eu.lightest.tpat.utils.DragDrop.*;
import eu.lightest.tpat.utils.Exceptions.*;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.CategoryState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import eu.lightest.tpat.utils.NL2TPLstateMachine.SubStates.ExpressionSubStates.IfState;
import eu.lightest.tpat.utils.Parser.Parser;
import javafx.scene.layout.Pane;
import eu.lightest.tpat.mvc.model.NaturalStatementModel;
import eu.lightest.tpat.utils.NL2TPLstateMachine.NL2TPL;

import java.util.List;
import java.util.stream.Collectors;

public class NaturalStatementController implements DropSpaceController {

  private NaturalStatementModel mModel;
  private DragDropHandler mDragDropHandler;
  public NaturalLanguageController mParentController;
  public NaturalStatementView mView;


  public NaturalStatementController(NaturalStatementView view) {
    mView = view;
  }

  public void init(NaturalLanguageController parentController, NaturalStatementModel statementModel, DragDropHandler dragDropHandler) {
    mParentController = parentController;
    mDragDropHandler = dragDropHandler;
    mModel = statementModel;
    mModel.setmController(this);

    setupDomainComboBox();

    mDragDropHandler.addDropHandling(mView.getContentPane(), this);
    if (mModel.getBlockList() != null && !mModel.getBlockList().isEmpty()) {
      restoreBlocks();
    }

    mView.getTitledPane().focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        mModel.getNaturalLanguageModel().setActiveStatement(mModel.getNaturalLanguageModel()
            .getNaturalStatementModels().indexOf(statementModel));
      }
    });
  }

  private void setupDomainComboBox() {
    mView.getDomainComboBox().getSelectionModel().select(mModel.getDomain());
    mView.getDomainComboBox().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      mModel.setDomain(newValue);
      mParentController.refreshTPLandSave(true);
      //TODO reset all value blocks
    });
  }

  @Override
  public void restoreBlocks() {
    for (SnapBlockModel model : mModel.getBlockList()) {
      SnapBlock snapBlock = BlockFactory.restoreSnapBlock(model);
      mView.getContentPane().getChildren().add(snapBlock.getBlockView());
      snapBlock.addDragHandling(mDragDropHandler, mParentController, this, mParentController);
    }
  }

  @Override
  public void addBlockToList(Block block, Pane pane) {
    mModel.getBlockList().add((SnapBlockModel) block.getBlockModel());
  }

  @Override
  public void removeCardFromList(Block block) {
    mModel.getBlockList().remove((SnapBlockModel) block.getBlockModel());
  }

  @Override
  public List<SnapBlockModel> getCards() {
    return this.mModel.getBlockList();
  }

  @Override
  public void onDropDone() {
    mParentController.refreshTPLandSave(true);
  }

  @Override
  public String getTemplateDomain() {
    return mModel.getDomain();
  }

  String refreshTPL() {
    String tplCode = "";
    try {
      for (BlockModel b : mModel.getBlockList()) {
        State s = b.getDetailModel().getSubState();
        if (s == null || !s.fullySpecified())
          throw new NotFullySpecifiedWarning();
      }

      List<SnapBlockModel> startingBlocks = mModel.getBlockList().stream().filter(p ->
          (p.getDetailModel() != null &&
              p.getDetailModel().getSubState() != null &&
              p.getDetailModel().getSubState() instanceof IfState)
      ).collect(Collectors.toList());

      if (startingBlocks.size() > 1)
        throw new DoubleStartWarningException();
      else if (startingBlocks.size() < 1)
        throw new NoStartWarningException();

      SnapBlockModel start = startingBlocks.get(0);

      NL2TPL nl2tpl = new NL2TPL(start);
      nl2tpl.check();
      mView.setNoError();

      if (mModel.getDomain() == null)
        throw new NoDomainChosenWarning();
      tplCode = nl2tpl.translate(Parser.sDomainList.get(mModel.getDomain()).getSpec());

    } catch (UserException ex) {
      if (ex.isError())
        mView.setError(ex.getMessage());
      else
        mView.setWarning(ex.getMessage());

      mParentController.handleUserException(ex);
    } catch (NullPointerException e) {
      // FIXME lib throws null exception because it goes on translating, although it found some errors
    }
    return tplCode;
  }

  Block createNewBlock(CategoryState state) {
    SnapBlock block = BlockFactory.newSnapBlock(state, this, mView.getContentPane());

    state.setBlock(block.getSnapBlockModel());
    block.addDragHandling(mDragDropHandler, mParentController, this, mParentController);

    mView.getContentPane().getChildren().add(block.getBlockView());
    mView.getContentPane().requestFocus();
    SnapBlock appendBlock = findBlockToAppend();
    if (appendBlock == null) {
      mParentController.positionNewBlock(block.getBlockView());
//      mView.getTitledPane().prefWidth()
    } else {
      appendBlock.snapTo(block, false);
    }
    DetailViewFactory.getInstance().create(block);
    refreshTPL();
    return block;
  }

  private SnapBlock findBlockToAppend() {
    List<SnapBlockView> startingBlockViews = mView.getContentPane().getChildren().stream()
        .filter(node -> (node instanceof SnapBlockView &&
            ((SnapBlockView) node).getController().getBlockModel().getDetailModel() != null &&
            ((SnapBlockView) node).getController().getBlockModel().getDetailModel().getSubState() instanceof IfState))
        .map(node -> (SnapBlockView) node)
        .collect(Collectors.toList());

    if (startingBlockViews.isEmpty())
      return null;
    SnapBlockView appendView = startingBlockViews.get(0);
    SnapBlockModel snapBlockModel = appendView.getController().getSnapBlockModel();
    while (snapBlockModel.mNext != null) {
      snapBlockModel = snapBlockModel.mNext;
    }

    return snapBlockModel.getController();
  }

  public double getHeight() {
    return mModel.getHeight();
  }

  public void setHeight(Number newValue) {
    mModel.setHeight(newValue);
  }
}
