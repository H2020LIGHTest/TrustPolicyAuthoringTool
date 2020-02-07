package eu.lightest.tpat.mvc.controller.DetailControllers;

import eu.lightest.tpat.mvc.view.DetailViews.DetailBinView;
import eu.lightest.tpat.utils.DragDrop.DragDropHandler;

public class DetailBinController {
  private DetailBinView mView;

  public void init(DragDropHandler dragDropHandler) {
    dragDropHandler.addDropEraser(mView.getBinLabel());

  }

  public DetailBinController(DetailBinView detailBinView) {
    mView = detailBinView;
  }
}
