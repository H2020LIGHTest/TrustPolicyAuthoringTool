package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.controller.DropSpaceController;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class DragDropHandler {
  private DataFormat mFormat;
  public boolean mIsDragInProgress = false;
  double mXOffset;
  double mYOffset;

  static Block mDraggingBlock;

  private static DragDropHandler sInstance;

  public static DragDropHandler getInstance() {
    if (sInstance == null) {
      sInstance = new DragDropHandler();
    }
    return sInstance;
  }

  private DragDropHandler() {
    mFormat = DataFormat.lookupMimeType("TP");
    if (mFormat == null)
      mFormat = new DataFormat("TP");
  }

  public void addDropHandling(Pane targetPane, DropSpaceController languageController) {

    targetPane.setOnDragOver(e -> {
      Dragboard db = e.getDragboard();
      if (db.hasContent(mFormat)
          && mDraggingBlock != null) {
        e.acceptTransferModes(TransferMode.MOVE);
        e.consume();
      }
    });

    targetPane.setOnDragDropped(e -> {
      Dragboard db = e.getDragboard();
      if (db.hasContent(mFormat)) {
        //TODO get rid of mDraggingBlock
        if (((Region) e.getGestureSource()).getParent() == targetPane) { //draged within same pane
          mDraggingBlock.getBlockView()
              .relocate(e.getX() - mXOffset, e.getY() - mYOffset);
          if (mDraggingBlock instanceof SnapBlock) {
            SnapBlockModel iterModel = ((SnapBlock) mDraggingBlock).getSnapBlockModel();
            int c = 0;
            while (iterModel.mNext != null) {
              c++;
              iterModel = iterModel.mNext;
              double h = iterModel.mPrev.getController().mBlockView.getHeight();
              iterModel.getController().getBlockView().relocate(iterModel.mPrev.getController().mBlockView.getLayoutX(), iterModel.mPrev.getController().mBlockView.getLayoutY() + h);
            }
          }
        } else { //dragged to other pane
          mDraggingBlock.mActualDropController.removeCardFromList(mDraggingBlock);
          ((Pane) mDraggingBlock.getBlockView().getParent())
              .getChildren().remove(mDraggingBlock.getBlockView());

          languageController.addBlockToList(mDraggingBlock, targetPane);
          mDraggingBlock.mActualDropController = languageController;
          targetPane.getChildren().add(mDraggingBlock.getBlockView());

          mDraggingBlock.getBlockView().relocate(e.getX() - mXOffset, e.getY() - mYOffset);

          if (mDraggingBlock instanceof SnapBlock) {
            SnapBlockModel iterModel = ((SnapBlock) mDraggingBlock).getSnapBlockModel();
            int c = 0;
            while (iterModel.mNext != null) {
              c++;
              iterModel = iterModel.mNext;
              double h = mDraggingBlock.mBlockView.getHeight();

              iterModel.getController().mActualDropController.removeCardFromList(iterModel.getController());
              ((Pane) iterModel.getController().getBlockView().getParent())
                  .getChildren().remove(iterModel.getController().getBlockView());

              languageController.addBlockToList(iterModel.getController(), targetPane);
              iterModel.getController().mActualDropController = languageController;
              targetPane.getChildren().add(iterModel.getController().getBlockView());

              iterModel.getController().getBlockView().relocate(mDraggingBlock.mBlockView.getLayoutX(), mDraggingBlock.mBlockView.getLayoutY() + h * c);
            }
          }
        }

        if (mDraggingBlock instanceof SnapBlock) {
          ((SnapBlock) mDraggingBlock).resetLinks(true);
          // snapping block workaround
          if (((SnapBlock) mDraggingBlock).mSnapToFinisch != null) {
            ((SnapBlock) mDraggingBlock).mSnapToFinisch.apply(null);
            ((SnapBlock) mDraggingBlock).mSnapToFinisch = null;
          }
        }

        e.setDropCompleted(true);
        db.setContent(null);

        languageController.onDropDone();
      }
    });
  }

  public void addDropEraser(Label targetPane) {
    targetPane.setOnDragOver(e -> {
      Dragboard db = e.getDragboard();
      if (db.hasContent(mFormat)
          && mDraggingBlock != null) {
        e.acceptTransferModes(TransferMode.MOVE);
        e.consume();
      }
    });
    targetPane.setOnDragDropped(e -> {
      Dragboard db = e.getDragboard();
      if (db.hasContent(mFormat)) { //draged within pane
        mDraggingBlock.destroyBlock();
        mIsDragInProgress = false;
        e.setDropCompleted(true);
        db.setContent(null);
      }
    });
  }

}
