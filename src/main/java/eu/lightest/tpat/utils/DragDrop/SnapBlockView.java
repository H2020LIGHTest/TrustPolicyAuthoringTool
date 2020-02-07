package eu.lightest.tpat.utils.DragDrop;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class SnapBlockView extends BlockView{
  private SnapBlock mSnapBlockController;

  private final int mDropPaneWidth;
  public final int mDropPaneHeight = 15;

  private final DropMarkerPane mDropPaneTop;
  private final DropMarkerPane mDropPaneBottom;

  public SnapBlockView(int width, int height) {
    super(width, height);
    mDropPaneWidth = width;

    mDropPaneTop = new DropMarkerPane(mDropPaneWidth, mDropPaneHeight);
    mDropPaneBottom = new DropMarkerPane(mDropPaneWidth, mDropPaneHeight);

    this.defineDropable(mDropPaneBottom, false);
    this.defineDropable(mDropPaneTop, true);

    this.getChildren().addAll(mDropPaneTop, mDropPaneBottom);

    AnchorPane.setTopAnchor(mDropPaneTop, 0.0);
    AnchorPane.setLeftAnchor(mDropPaneTop, 0.0);
    AnchorPane.setRightAnchor(mDropPaneTop, 0.0);

    AnchorPane.setBottomAnchor(mDropPaneBottom, 0.0);
    AnchorPane.setRightAnchor(mDropPaneBottom, 0.0);
    AnchorPane.setLeftAnchor(mDropPaneBottom, 0.0);
  }

  @Override
  public void setBlockController(Block blockController) {
    mSnapBlockController = (SnapBlock)blockController;
    mBlockController = blockController;
  }

  public void setRectAccordingToCategory() {
    mHeader.setTextFill(Color.LIGHTGRAY);
    Color color = mBlockController.getCategoryColor();
    mRect.setStroke(color);
    mRect.setFill(color.deriveColor(1, 1, 1, 1));
    mBinBtn.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%," +
        " rgba("+ (int)(color.getRed()*255) + "," + (int)(color.getGreen()*255) + "," + (int)(color.getBlue()*255) + ",1.0) 0%," +
        " rgba(255,255,255,0.0) 100%);");
  }

  private void defineDropable(DropMarkerPane target, boolean top) {
    target.setOnDragOver(e -> {
      Dragboard db = e.getDragboard();
      if (db.hasContent(mBlockController.mFormat)) {
        e.acceptTransferModes(TransferMode.MOVE);
        e.consume();
      }
    });

    target.setOnDragEntered(event -> {
      target.giveColor();
    });

    target.setOnDragExited(event -> {
      target.hideColor();
    });

    // when dropped on other snapping block
    target.setOnDragDropped(e -> {
      Dragboard db = e.getDragboard();
      if (db.hasContent(mBlockController.getFormat())) {//draged within pane
        SnapBlock draggingBlock = (SnapBlock) DragDropHandler.mDraggingBlock;
        draggingBlock.mSnapToFinisch = (o -> {
          if (draggingBlock.getBlockView().getParent() == this.getParent()) {
            mSnapBlockController.snapTo(draggingBlock, top);
            e.setDropCompleted(true);
            db.setContent(null);
          }
          return null;
        });
      }
    });
  }

  public void hideOwnDropAreas() {
    mDropPaneTop.makeUnvisible();
    mDropPaneBottom.makeUnvisible();
  }

  public DropMarkerPane getDropPaneTop() {
    return mDropPaneTop;
  }

  public DropMarkerPane getDropPaneBottom() {
    return mDropPaneBottom;
  }

  public SnapBlock getController() {
    return mSnapBlockController;
  }
}
