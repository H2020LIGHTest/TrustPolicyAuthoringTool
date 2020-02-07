package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.utils.DragDrop.BlockView;

public abstract class UsingBlockController extends UndoRedoController {
  int mSpawnX = 100;
  int mSpawnY = 100;

  void positionNewBlock(BlockView blockView) {
    mSpawnX = (mSpawnX + 10) % 50;
    mSpawnY = (mSpawnY + 10) % 50;
    blockView.relocate(mSpawnX, mSpawnY);
  }
}
