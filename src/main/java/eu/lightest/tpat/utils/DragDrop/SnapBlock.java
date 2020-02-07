package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.controller.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.input.TransferMode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SnapBlock extends Block {
  Function<Object, Object> mSnapToFinisch;
  private SnapBlockModel mSnapBlockModel;
  private SnapBlockView mSnapBlockView;

  public SnapBlock(SnapBlockModel snapBlockModel, SnapBlockView snapBlockView) {
    super(snapBlockModel, snapBlockView);
    mSnapBlockModel = snapBlockModel;
    mSnapBlockView = snapBlockView;
    mSnapBlockView.setBlockController(this);
    snapBlockModel.setController(this);

    mSnapBlockView.setRectAccordingToCategory();
  }


  public void resetLinks(boolean dragging) {
    if (mSnapBlockModel.mPrev != null)
      mSnapBlockModel.mPrev.mNext = null;

    if (!dragging && mSnapBlockModel.mNext != null)
      mSnapBlockModel.mNext.mPrev = null;

    mSnapBlockModel.mPrev = null;

    if (!dragging)
      mSnapBlockModel.mNext = null;
  }

  public void snapTo(SnapBlock snapBlock, boolean top) {
    if (top && mSnapBlockModel.mPrev == null) {
      snapBlock.getBlockView().relocate(mBlockView.getLayoutX(), mBlockView.getLayoutY() - mBlockView.getHeight());
      mSnapBlockModel.mPrev = snapBlock.getSnapBlockModel();
      snapBlock.getSnapBlockModel().mNext = this.mSnapBlockModel;
    } else if (!top && mSnapBlockModel.mNext == null) {
      snapBlock.getBlockView().relocate(mBlockView.getLayoutX(), mBlockView.getLayoutY() + mBlockView.getHeight());
      mSnapBlockModel.mNext = snapBlock.getSnapBlockModel();
      snapBlock.getSnapBlockModel().mPrev = this.mSnapBlockModel;

      SnapBlockModel iterModel = snapBlock.getSnapBlockModel();
      while (iterModel.mNext != null) {
        BlockView prevView = iterModel.getController().getBlockView();
        iterModel = iterModel.mNext;
        double h = this.mBlockView.getHeight();
        iterModel.getController().getBlockView().relocate(prevView.getLayoutX(), prevView.getLayoutY() + h);
      }
    }
  }


  @Override
  public void addDragHandling(DragDropHandler dragDropHandler, IDetailViewListener dvt, DropSpaceController dc, IBaseController bc) {
    super.addDragHandling(dragDropHandler, dvt, dc, bc);

    mDragDetectedQueue.add(event -> {
      showAllDropAreas(null);

      List<WritableImage> imageList = new ArrayList<>();
      imageList.add(mSnapShot);
      BufferedImage snapBuffered = SwingFXUtils.fromFXImage(mSnapShot, null);
      SnapBlockModel iterModel = this.mSnapBlockModel;
      while (iterModel.mNext != null) {
        iterModel = iterModel.mNext;
        imageList.add(iterModel.getController().getBlockView().snapshot(null, null));
        iterModel.getController().getBlockView().setVisible(false);
      }

      BufferedImage image = new BufferedImage(snapBuffered.getWidth(),
          snapBuffered.getHeight() * imageList.size(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D graphics = (Graphics2D) image.getGraphics();
      graphics.setBackground(Color.BLACK);
      graphics.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//      graphics.drawRect(0,0,400,400);

      int coutner = 0;
      for (WritableImage i : imageList) {
        BufferedImage img = SwingFXUtils.fromFXImage(i, null);
        graphics.drawImage(img, 0, img.getHeight() * coutner, null);
        coutner++;
      }

      WritableImage combi = SwingFXUtils.toFXImage(image, null);
      mDragBoard.setDragView(combi);
    });


    mDragDoneQueue.add(() -> {
      hideAllDropAreas(null);

      SnapBlockModel iterModel = this.mSnapBlockModel;
      iterModel.getController().getBlockView().setVisible(true);
      while (iterModel.mNext != null) {
        iterModel = iterModel.mNext;
        iterModel.getController().getBlockView().setVisible(true);
      }
    });

  }

  public void hideOwnDropAreas() {
    mSnapBlockView.hideOwnDropAreas();
  }

  public void showOwnDropAreas() {
    if (mSnapBlockModel.mPrev == null)
      mSnapBlockView.getDropPaneTop().makeVisible();
    if (mSnapBlockModel.mNext == null)
      mSnapBlockView.getDropPaneBottom().makeVisible();
  }

  @Override
  public void showAllDropAreas(IBaseController controller) {
    if (controller != null)
      ((NaturalLanguageController) controller).showAllDropAreas(this);
    else
      ((NaturalStatementController) mActualDropController).mParentController.showAllDropAreas(this);
  }

  @Override
  public void hideAllDropAreas(IBaseController controller) {
    if (controller != null)
      ((NaturalLanguageController) controller).hideAllDropAreas();
    else
      ((NaturalStatementController) mActualDropController).mParentController.hideAllDropAreas();
  }


  @Override
  public void destroyBlock() {
    resetLinks(false);
    super.destroyBlock();
  }

  public SnapBlockModel getSnapBlockModel() {
    return mSnapBlockModel;
  }
}
