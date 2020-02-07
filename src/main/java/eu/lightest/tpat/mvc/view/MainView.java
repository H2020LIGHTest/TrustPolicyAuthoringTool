package eu.lightest.tpat.mvc.view;

import eu.lightest.tpat.mvc.controller.DetailControllers.DetailViewFactory;
import eu.lightest.tpat.mvc.controller.NaturalLanguageController;
import eu.lightest.tpat.utils.CustomComponents.AmazonasCell;
import eu.lightest.tpat.utils.DragDrop.DragDropHandler;
import eu.lightest.tpat.utils.Utils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import eu.lightest.tpat.mvc.controller.MainController;
import org.apache.log4j.Logger;


public class MainView extends Application {
  private final MainController mMainController = new MainController(this);

  public static Scene sScene;

  private static Logger logger = Logger.getLogger(MainView.class);

  @FXML
  ImageView mLogo;

  @FXML
  Button mAddBtn;

  public static void main(String[] args) {
    launch(args);
  }

  @FXML
  Pane mTPpane;

  @FXML
  ListView<TrustPolicyModel> mTpListView;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/layouts/main_layout.fxml"));
    primaryStage.setTitle("Welcome to the LIGHTest experience.");
    primaryStage.getIcons().addAll(
        new Image("images/USTUTT/48x48/Desktop-Icon-1.png", 48,48,true,true)
    );
    sScene = new Scene(root);
    //set theme
    sScene.getStylesheets().add("/global_style_sheet.css");
    root.setStyle("-fx-font-weight: bold");
//    mScene.getStylesheets().add("/Material.css");
//    new JMetro(JMetro.Style.LIGHT).applyTheme(root);
//    FlatterFX.getInstance().getOverlayLayer(mScene);
//    mScene.getStylesheets().add("/bootstrap3.css");
    primaryStage.setScene(sScene);
    primaryStage.show();

    sScene.focusOwnerProperty().addListener((observable, oldValue, newValue) -> {
      Node newSelection = sScene.focusOwnerProperty().get();
      logger.debug("focus changed to: " + newSelection);
      if (DetailViewFactory.sDetailPane != null &&
          newSelection != null &&
          !Utils.containedInView(DetailViewFactory.sDetailPane, newSelection)) {
        DetailViewFactory.getInstance().resetDetailView(getMainController().getLanguageView(), DragDropHandler.getInstance());
      }
    });
  }

  @FXML
  public void initialize() {
    mMainController.init();
    mTpListView.setItems(FXCollections.observableList(mMainController.getMainModel().getTrustPolicyModels()));
    mTpListView.setCellFactory(param -> new AmazonasCell(this, mTpListView));
    mLogo.setImage(new Image("/images/USTUTT/logo.png"));
  }

  @FXML
  public void onAddClicked() {
    mMainController.openDialogs();
  }


  public Pane getTPpane() {
    return mTPpane;
  }

  public ListView<TrustPolicyModel> getTpListView() {
    return mTpListView;
  }

  public MainController getMainController() {
    return mMainController;
  }
}
