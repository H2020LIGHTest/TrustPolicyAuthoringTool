package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.utils.DragDrop.Block;

public interface IDetailViewListener {
  void onDetailViewSelected(Block assocBlock);

  void onDetailViewDeselected();

  void onDragInProgress();
}
