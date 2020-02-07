package eu.lightest.tpat.utils.DragDrop;

import eu.lightest.tpat.mvc.controller.IBaseController;

public class GraphBlock extends Block {

  private final BlockView mGraphBlockView;
  private final GraphBlockModel mGraphBlockModel;

  public GraphBlock(GraphBlockModel graphBlockModel, BlockView graphBlockView) {
    super(graphBlockModel, graphBlockView);
    mGraphBlockModel = graphBlockModel;
    mGraphBlockView = graphBlockView;
    graphBlockView.setBlockController(this);
    graphBlockModel.setBlockController(this);
  }

  @Override
  public void showAllDropAreas(IBaseController controller) {
    // Does not have
  }

  @Override
  public void hideAllDropAreas(IBaseController controller) {
    // does not have
  }

  public GraphBlockModel getGraphBlockModel() {
    return mGraphBlockModel;
  }

}
