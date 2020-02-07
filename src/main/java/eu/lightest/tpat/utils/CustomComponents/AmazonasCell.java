package eu.lightest.tpat.utils.CustomComponents;

import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import eu.lightest.tpat.mvc.view.MainView;
import eu.lightest.tpat.utils.ReschedulableTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class AmazonasCell extends ListCell<TrustPolicyModel> {
  enum State {
    DELETED,
    NORMAL
  }

  private final ListView<TrustPolicyModel> mTpListView;
  private final MainView mMainView;

  Button mDelBtn = new Button();

  Label mUndoLabel = new Label("Undo");


  private State mState = State.NORMAL;
  ReschedulableTimer mTimer;

  public AmazonasCell(MainView mainView, ListView<TrustPolicyModel> tpllist) {
    mTpListView = tpllist;
    mMainView = mainView;

    mDelBtn.getStyleClass().add("bin-button");
    this.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        mDelBtn.setStyle("-icon-paint: white;");
      } else {
        mDelBtn.setStyle("-icon-paint: black;");
      }
    });
  }

  @Override
  protected void updateItem(TrustPolicyModel item, boolean empty) {
    super.updateItem(item, empty);

    if (item != null && !empty) {
      switch (mState) {
        case NORMAL:
          renderNormalTplCell(item);
          break;
        case DELETED:
          renderDeletedCell(item);
      }
    } else {
      setGraphic(null);
      setText(null);
    }
  }

  private void renderNormalTplCell(TrustPolicyModel item) {
    setText(null);
    TextField nameField = new TextField(item.mName);
    nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        item.mName = nameField.getText();
        mMainView.getTpListView().refresh();
      }
    });

    ImageView img = null;
    switch (item.mType) {
      case graphical:
        img = new ImageView(new Image("/images/USTUTT/32x32/GE.png"));
        break;
      case natural:
        img = new ImageView(new Image("/images/USTUTT/32x32/NL.png"));
        break;
      default:
        img = new ImageView(new Image("/images/USTUTT/32x32/TPL.png"));
        break;
    }

    mDelBtn.setOnAction(event -> deletingItem(item));

    HBox.setHgrow(nameField, Priority.ALWAYS);
    nameField.setMinWidth(50);
    nameField.setPrefWidth(50);

    HBox hbox = new HBox(img, nameField, mDelBtn);
    hbox.setSpacing(8);
    hbox.setAlignment(Pos.CENTER_LEFT);
    setGraphic(hbox);
    img.setFitHeight(32);
    img.setFitWidth(32);

    setOnMouseClicked(event -> mMainView.getMainController().choseTrustPolicyView(item));
  }

  private void renderDeletedCell(TrustPolicyModel item) {
    mUndoLabel.setStyle("-fx-font-weight: bold; -fx-underline: true");
    if (this.isSelected()){
      mUndoLabel.setTextFill(Color.WHITESMOKE);
    } else {
      mUndoLabel.setTextFill(Color.BLUE);
    }

    this.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue){
        mUndoLabel.setTextFill(Color.WHITESMOKE);
      } else {
        mUndoLabel.setTextFill(Color.BLUE);
      }
    });

    mUndoLabel.setOnMouseClicked(event -> {
      mTimer.cancel();
      restoreItem(item);
    });
    setOnMouseClicked(null);

    this.setGraphic(mUndoLabel);
  }

  private void deletingItem(TrustPolicyModel item) {
    mState = State.DELETED;
    if (mMainView.getMainController().getActModel() != null && mMainView.getMainController().getActModel().equals(item)) {
      mMainView.getMainController().createDefaultView();
    }

    mTimer = new ReschedulableTimer(() -> {
      Platform.runLater(() -> {
        restoreItem(item); //first to make sure, deleted in a safe way
        mMainView.getMainController().delete(item);
        mTpListView.refresh();
      });
    });
    mTimer.schedule(5000);

    renderDeletedCell(item);
  }

  private void restoreItem(TrustPolicyModel item) {
    mState = State.NORMAL;
    renderNormalTplCell(item);
  }
}
