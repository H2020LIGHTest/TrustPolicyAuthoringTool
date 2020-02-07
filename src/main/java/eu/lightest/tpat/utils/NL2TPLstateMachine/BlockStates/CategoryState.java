package eu.lightest.tpat.utils.NL2TPLstateMachine.BlockStates;

import eu.lightest.tpat.utils.DragDrop.BlockModel;
import eu.lightest.tpat.utils.NL2TPLstateMachine.State;
import javafx.scene.paint.Color;

public abstract class CategoryState implements State, java.io.Serializable {
  BlockModel mBlockModel;

  public void setBlock(BlockModel mBlockModel) {
    this.mBlockModel = mBlockModel;
  }

  public abstract String getLayout();

  public abstract Color getCategoryColor();

  @Override
  public boolean fullySpecified() {
    return true;
  }
}
