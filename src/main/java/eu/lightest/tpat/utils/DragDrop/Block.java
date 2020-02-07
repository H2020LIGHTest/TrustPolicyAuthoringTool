package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailViewFactory;
import eu.lightest.tpat.mvc.controller.DropSpaceController;
import eu.lightest.tpat.mvc.controller.IDetailViewListener;
import eu.lightest.tpat.mvc.controller.IBaseController;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Block {

  protected BlockView mBlockView;

  protected BlockModel mBlockModel;

  protected DataFormat mFormat;

  public DropSpaceController mActualDropController;

  public IBaseController mActualBaseController;
  private IDetailViewListener mDetailViewListener;
  List<Runnable> mDragDoneQueue = new ArrayList<Runnable>();

  List<Consumer<MouseEvent>> mDragDetectedQueue = new ArrayList<Consumer<MouseEvent>>();
  WritableImage mSnapShot;
  Dragboard mDragBoard;

  public Block(BlockModel blockModel, BlockView blockView) {
    mBlockModel = blockModel;
    mBlockView = blockView;
    //Dragdrop things
    mFormat = DataFormat.lookupMimeType("TP");
    if (mFormat == null)
      mFormat = new DataFormat("TP");

    mBlockView.setTitle(mBlockModel.getState().getName());
    if (mBlockModel.getDetailModel() != null &&
        mBlockModel.getDetailModel().getSubState() != null &&
        mBlockModel.getDetailModel().getSubState().getName() != null) {
      mBlockView.setTitle(mBlockModel.getDetailModel().getSubState().getName());
    }

    mBlockView.setOnMouseClicked(event -> {
      mDetailViewListener.onDetailViewSelected(this);
      event.consume();
    });

    mBlockView.layoutXProperty().addListener((observable, oldValue, newValue) -> mBlockModel.setmXpos(newValue));
    mBlockView.layoutYProperty().addListener((observable, oldValue, newValue) -> mBlockModel.setmYpos(newValue));
  }


  public void addDragHandling(DragDropHandler dragDropHandler, IDetailViewListener dvt, DropSpaceController dc, IBaseController bc) {
    mActualBaseController = bc;
    mActualDropController = dc;
    mDetailViewListener = dvt;


    mDragDetectedQueue.add((event) -> {
      mDragBoard = mBlockView.startDragAndDrop(TransferMode.MOVE);
      mDragBoard.setDragViewOffsetX(event.getX());
      mDragBoard.setDragViewOffsetY(event.getY());
      dragDropHandler.mXOffset = event.getX();
      dragDropHandler.mYOffset = event.getY();
      if (mSnapShot == null)
        mSnapShot = mBlockView.snapshot(null, null);
      mDragBoard.setDragView(mSnapShot);
      ClipboardContent cc = new ClipboardContent();
      cc.put(mFormat, "block");
      mDragBoard.setContent(cc);
      dragDropHandler.mDraggingBlock = this;
      mBlockView.setVisible(false);
    });

    mDragDoneQueue.add(() -> {
      dragDropHandler.mDraggingBlock = null;
      mBlockView.setVisible(true);
    });

    mDragDetectedQueue.add((event) -> {
      showBin();
      dragDropHandler.mIsDragInProgress = true;
    });

    mDragDoneQueue.add(() -> {
      DetailViewFactory.getInstance().create(this);
      dragDropHandler.mIsDragInProgress = false;
    });

    mBlockView.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        mDetailViewListener.onDetailViewSelected(this);
      }
    });

    mBlockView.setOnDragDetected(event -> {
      for (Consumer<MouseEvent> r : mDragDetectedQueue)
        r.accept(event);
      event.consume();
    });

    mBlockView.setOnDragDone(e -> {
      for (Runnable r : mDragDoneQueue)
        r.run();
      e.consume();
    });

  }

  private void showBin() {
    mDetailViewListener.onDragInProgress();
  }

  public void destroyBlock() {
    this.mActualDropController.removeCardFromList(this);
    ((Pane) mBlockView.getParent()).getChildren().remove(mBlockView);
    mActualBaseController.closeDetailView();
    mActualBaseController.refreshTPLandSave(true);
  }

  public abstract void showAllDropAreas(IBaseController controller);

  public abstract void hideAllDropAreas(IBaseController controller);

  public String getHeaderText() {
    return mBlockView.getTitel();
  }

  public void setHeaderText(String text) {
//    int max_length = 20;
//    if (text.length() > max_length)
//      text = "..." + text.substring(text.length() - max_length);

    mBlockView.setTitle(text);
  }

  public DropSpaceController getActualDragBlockController() {
    return mActualDropController;
  }

  public Color getCategoryColor() {
    return mBlockModel.getState().getCategoryColor();
  }

  public DataFormat getFormat() {
    return mFormat;
  }

  public BlockView getBlockView() {
    return mBlockView;
  }

  public BlockModel getBlockModel() {
    return mBlockModel;
  }
}
