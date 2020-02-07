package eu.lightest.tpat.utils.DragDrop;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DropMarkerPane extends Pane {
  private final Color mDropMarkColor = new Color(1.0, 1.0, 0.0, 1.0);
  private final Rectangle mDropRect;


  DropMarkerPane(int width, int height) {
    super();
    mDropRect = new Rectangle(width, height);
    this.hideColor();
    this.getChildren().add(mDropRect);
  }

  public void makeVisible() {
    this.giveBorder();
  }

  public void makeUnvisible() {
    this.hideBorder();
  }

  public void giveColor() {
    mDropRect.setFill(mDropMarkColor.deriveColor(1, 1, 1, 1));
  }

  public void hideColor() {
    mDropRect.setFill(mDropMarkColor.deriveColor(1, 1, 1, 0));
  }
  public void giveBorder() {
    mDropRect.setStroke(mDropMarkColor.deriveColor(1, 1, 1, 1));
  }

  public void hideBorder() {
    mDropRect.setStroke(mDropMarkColor.deriveColor(1, 1, 1, 0));
  }
}
