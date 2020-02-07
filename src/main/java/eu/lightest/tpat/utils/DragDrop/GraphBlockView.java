package eu.lightest.tpat.utils.DragDrop;

public class GraphBlockView extends BlockView {
  public GraphBlockView(int width, int height) {
    super(width, height);
  }

  @Override
  public void setBlockController(Block blockController) {
    mBlockController = (Block) blockController;
  }
}
