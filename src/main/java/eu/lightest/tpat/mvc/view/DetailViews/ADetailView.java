package eu.lightest.tpat.mvc.view.DetailViews;

import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.utils.DragDrop.Block;

public abstract class ADetailView {
  Block mBlock;

  public abstract void initController(Block assocBlock, IBaseController baseController);
}
