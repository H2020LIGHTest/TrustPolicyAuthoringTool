package eu.lightest.tpat.mvc.controller.DetailControllers;

import eu.lightest.tpat.mvc.controller.IBaseController;
import eu.lightest.tpat.mvc.view.*;
import eu.lightest.tpat.mvc.view.DetailViews.DetailBinView;
import eu.lightest.tpat.mvc.view.DetailViews.ADetailView;
import eu.lightest.tpat.utils.DragDrop.Block;
import eu.lightest.tpat.utils.DragDrop.DragDropHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DetailViewFactory {

  public static Pane sDetailPane;
  public static ADetailView sDetailView;

  private static DetailViewFactory sInstance;

  private DetailViewFactory(){
  }

  public static DetailViewFactory getInstance() {
    if (sInstance == null){
      sInstance = new DetailViewFactory();
    }

    return sInstance;
  }

  public void create(Block assocBlock) {
    String layout = assocBlock.getBlockModel().getState().getLayout();
    buildView(assocBlock, layout, assocBlock.mActualBaseController);
  }

  public void buildBinView( ILanguageView view, DragDropHandler dragDropHandler) {
    try {
      view.getDetailPane().getChildren().clear();
      FXMLLoader loader = new FXMLLoader();
      sDetailPane = loader.load(getClass().getResource("/layouts/detail_layouts/detail_bin_layout.fxml").openStream());
      view.getDetailPane().getChildren().add(sDetailPane);
      DetailBinView detailBinView = (DetailBinView) loader.getController();
      detailBinView.initController(dragDropHandler);
      //MAGIC FOR FITTING PARENT
      sDetailPane.prefWidthProperty().bind(view.getDetailPane().widthProperty());
      sDetailPane.prefHeightProperty().bind(view.getDetailPane().heightProperty());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void resetDetailView(ILanguageView view, DragDropHandler dragDropHandler) {
    if (!dragDropHandler.mIsDragInProgress && view.getDetailPane() != null) {
      view.getDetailPane().getChildren().clear();
      VBox container = new VBox();
      container.setSpacing(8);
      container.setAlignment(Pos.TOP_CENTER);
      view.getDetailPane().getChildren().add(container);
      ImageView imageView = new ImageView(new Image("images/USTUTT/48x48/Desktop-Icon-2.png", 48,48,true,true));
      Label label = new Label("Select a building block on the top and add it to your statement or adjust existing blocks with Drag and Drop.");
      label.setWrapText(true);
      container.setPadding(new Insets(8));
      container.getChildren().addAll(imageView, label);
      container.prefWidthProperty().bind(view.getDetailPane().widthProperty());
      container.prefHeightProperty().bind(view.getDetailPane().heightProperty());
    }
  }

  private void buildView(Block assocBlock, String layout, IBaseController baseController) {
    try {
      baseController.getBaseView().getDetailPane().getChildren().clear();
      FXMLLoader loader = new FXMLLoader();
      sDetailPane = loader.load(getClass().getResource(layout).openStream());
      sDetailView = (ADetailView)loader.getController();
      sDetailView.initController(assocBlock, baseController);
      baseController.getBaseView().getDetailPane().getChildren().add(sDetailPane);
      //MAGIC FOR FITTING PARENT
      sDetailPane.prefWidthProperty().bind(baseController.getBaseView().getDetailPane().widthProperty());
      sDetailPane.prefHeightProperty().bind(baseController.getBaseView().getDetailPane().heightProperty());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
