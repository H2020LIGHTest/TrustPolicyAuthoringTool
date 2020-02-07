package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.mvc.view.NaturalStatementView;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.DragDrop.BlockView;
import eu.lightest.tpat.utils.DragDrop.SnapBlockModel;
import javafx.scene.layout.Pane;

import java.util.List;

public interface DropSpaceController {

  void addBlockToList(Block block, Pane pane);

  void removeCardFromList(Block block);

  void restoreBlocks();

  List<SnapBlockModel> getCards();

  void onDropDone();

  String getTemplateDomain();
}
