package eu.lightest.tpat.utils.DragDrop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public abstract class BlockView extends AnchorPane {
  protected Block mBlockController;

  //  protected final int mWidth = 200;
  //  protected final int mHeight = 50;
  protected final Rectangle mRect;
  protected Label mHeader = new Label("");

  protected Button mBinBtn = new Button();
  protected static Random mRand = new Random();


  public BlockView(int width, int height) {
    mRect = new Rectangle(width, height);
    setWidth(width);
    setHeight(height);
    mHeader.setStyle("-fx-font-weight: bold");
    mHeader.setWrapText(true);

    mHeader.prefWidthProperty().bind(this.widthProperty());
    mHeader.prefWidthProperty().bind(this.mRect.widthProperty());
    this.managedProperty().bind(this.visibleProperty());

    // Rectangle
    Color color = createRandomColor();
    mRect.setStroke(color);
    mRect.setFill(color.deriveColor(0, 1, 1, 1));

    HBox btnBox = new HBox(mBinBtn);
    btnBox.setAlignment(Pos.CENTER);
    btnBox.setPadding(new Insets(8.0));

    mBinBtn.setGraphic(new ImageView(new Image("/images/USTUTT/24x24/white_trash.png", 24, 24, true, true)));
    mBinBtn.getStyleClass().add("trans-button");
    mBinBtn.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 100%, rgba("
        + (int)(color.getRed()*255) + "," + (int)(color.getGreen()*255) + "," + (int)(color.getBlue()*255) + ",1.0) 0%, rgba(255,255,255,0.0) 100%);");
    mBinBtn.setOnAction(event -> mBlockController.destroyBlock());

    this.getChildren().addAll(mRect, mHeader, btnBox);

    AnchorPane.setRightAnchor(btnBox, 0.0);
    AnchorPane.setBottomAnchor(btnBox, 0.0);
    AnchorPane.setTopAnchor(btnBox, 0.0);

    AnchorPane.setLeftAnchor(mHeader, 0.0);
    AnchorPane.setRightAnchor(mHeader, 0.0);
    AnchorPane.setBottomAnchor(mHeader, 0.0);
    AnchorPane.setTopAnchor(mHeader, 0.0);
    mHeader.setAlignment(Pos.CENTER);


    if (color.getBrightness() > 0.7)
      mHeader.setTextFill(Color.BLACK);
    else
      mHeader.setTextFill(Color.WHITESMOKE);
    mHeader.setPadding(new Insets(4));
    mHeader.setAlignment(Pos.CENTER_LEFT);
  }

  protected Color createRandomColor() {
    int max = 200;
    return Color.rgb((int) (mRand.nextDouble() * max), (int) (mRand.nextDouble() * max), (int) (mRand.nextDouble() * max));
  }

  public void setTitle(String text) {
    mHeader.setText(text);
  }

  public String getTitel() {
    return mHeader.getText();
  }

  public abstract void setBlockController(Block blockController);

  public void setPosition(Number xpos, Number ypos) {
    this.relocate(xpos.longValue(), ypos.longValue());
  }
}
