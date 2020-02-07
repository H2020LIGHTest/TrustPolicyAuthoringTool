package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.controller.DropSpaceController;
import eu.lightest.tpat.mvc.controller.GraphicalLanguageController;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.CategoryState;
import eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates.TrustSchemeState;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class BlockFactory {

  public static SnapBlock newSnapBlock(CategoryState state, DropSpaceController dc, Pane targetPane) {
    SnapBlockModel snapBlockModel = new SnapBlockModel(state, null);

    SnapBlockView snapBlockView = new SnapBlockView(200, 50);
    SnapBlock snapBlock = new SnapBlock(snapBlockModel, snapBlockView);
    dc.addBlockToList(snapBlock, targetPane);
    return snapBlock;
  }

  public static SnapBlock restoreSnapBlock(SnapBlockModel snapBlockModel) {
    SnapBlockView snapBlockView = new SnapBlockView(200, 50);
    snapBlockView.setPosition(snapBlockModel.getmXpos(), snapBlockModel.getmYpos());
    return new SnapBlock(snapBlockModel, snapBlockView);
  }

  public static GraphBlock newGraphBlock(GraphicalLanguageController graphicalLanguageController, AnchorPane partOfPane) {
    GraphBlockModel model = new GraphBlockModel(null, new TrustSchemeState());
    GraphBlockView view = new GraphBlockView(200, 50);
    GraphBlock graphBlock =  new GraphBlock(model, view);
    graphicalLanguageController.addBlockToList(graphBlock, partOfPane);
    return graphBlock;
  }

  public static GraphBlock restoreGraphBlock(GraphBlockModel blockModel) {
    GraphBlockView view = new GraphBlockView(200, 50);
    view.setPosition(blockModel.getmXpos(), blockModel.getmYpos());
    return new GraphBlock(blockModel, view);
  }
}
