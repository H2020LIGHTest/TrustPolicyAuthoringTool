package eu.lightest.tpat.utils.CustomComponents;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class ResizeManager {
  private static final int RESIZE_MARGIN = 32;

  private final Region mRegion;

  private double y;
  private double x;

  private boolean initMinHeight;
  private boolean initMinWidth;

  private boolean dragging;

  private ResizeManager(Region region) {
    mRegion = region;
  }

  public static void makeResizable(Region region) {
    final ResizeManager resizer = new ResizeManager(region);

    region.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resizer.mousePressed(event);
      }});
    region.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resizer.mouseDragged(event);
      }});
    region.setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resizer.mouseOver(event);
      }});
    region.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resizer.mouseReleased(event);
      }});
  }

  protected void mouseReleased(MouseEvent event) {
    dragging = false;
    mRegion.setCursor(Cursor.DEFAULT);
  }

  protected void mouseOver(MouseEvent event) {
    if(isInDraggableZone(event) || dragging) {
      mRegion.setCursor(Cursor.SE_RESIZE);
    }
    else {
      mRegion.setCursor(Cursor.DEFAULT);
    }
  }

  protected boolean isInDraggableZone(MouseEvent event) {
    return event.getY() > (mRegion.getHeight() - RESIZE_MARGIN)
        && event.getX() > (mRegion.getWidth() - RESIZE_MARGIN);
  }

  protected void mouseDragged(MouseEvent event) {
    if(!dragging) {
      return;
    }

    double mouseY = event.getY();
    double mouseX = event.getX();

    double newHeight = mRegion.getMinHeight() + (mouseY - y);
    double newWidth = mRegion.getMinWidth() + (mouseX - x);

    mRegion.setMinHeight(newHeight);

    double maxWidth = mRegion.getParent().getBoundsInParent().getWidth();
    mRegion.setMinWidth(Math.min(maxWidth, newWidth));


    y = mouseY;
    x = mouseX;
  }

  protected void mousePressed(MouseEvent event) {

    if(!isInDraggableZone(event)) {
      return;
    }

    dragging = true;

    if (!initMinHeight) {
      mRegion.setMinHeight(mRegion.getHeight());
      initMinHeight = true;
    }
    if (!initMinWidth) {
      mRegion.setMinWidth(mRegion.getWidth());
      initMinWidth = true;
    }

    x = event.getX();
    y = event.getY();
  }
}
