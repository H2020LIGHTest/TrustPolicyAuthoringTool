package eu.lightest.tpat.mvc.controller;

import eu.lightest.tpat.mvc.model.*;
import eu.lightest.tpat.mvc.view.*;
import eu.lightest.tpat.utils.Parser.Parser;
import eu.lightest.tpat.utils.SaveManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import eu.lightest.tpat.utils.CustomStage;
import org.apache.log4j.Logger;

import java.io.*;

public class MainController {

  private static ILanguageView sLanguageView;

  private final MainModel mMainModel = new MainModel();

  private final MainView mMainView;

  private Pane mTPpane;
  private TrustPolicyModel mActModel;

  private static Logger logger = Logger.getLogger(MainController.class);

  public MainController(MainView mainView) {
    this.mMainView = mainView;
  }

  public void init() {
    createDefaultView();
    Parser.discover();
    SaveManager.loadSaves(mMainModel);
  }


  public void createTrustPolicy(String name, TrustPolicyModel.TplType type) {
    TrustPolicyModel tpm = addTPEntrie(type, name);
    choseTrustPolicyView(tpm);
  }

  public void choseTrustPolicyView(TrustPolicyModel model) {
    try {
      switch (model.mType) {
        case graphical:
          createGraphicalView(model);
          break;
        case natural:
          createNatualView(model);
          break;
        case script:
          createScriptView(model);
          break;
        default:
          throw new Exception("No policy type like that");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private TrustPolicyModel addTPEntrie(TrustPolicyModel.TplType tplType, String name) {
    TrustPolicyModel tpm;
    switch (tplType) {
      case graphical:
        tpm = new GraphicalLanguageModel(name, tplType);
        break;
      case natural:
        tpm = new NaturalLanguageModel(name, tplType);
        break;
      case script:
        tpm = new ScriptLanguageModel(name, tplType);
        break;
      default:
        return null;
    }
    mMainModel.getTrustPolicyModels().add(tpm);
    mMainView.getTpListView().setItems(FXCollections.observableList(getMainModel().getTrustPolicyModels()));
    mMainView.getTpListView().getSelectionModel().select(tpm);
    return tpm;
  }

  private FXMLLoader createBasicTrustPolicyView(String s) throws IOException {
    mTPpane = mMainView.getTPpane();
    mTPpane.getChildren().clear();
    FXMLLoader loader = new FXMLLoader();
    Region r = loader.load(getClass().getResource(s).openStream());
    mTPpane.getChildren().add(r);
    //MAGIC FOR FILLING PARENT
    r.prefWidthProperty().bind(mTPpane.widthProperty());
    r.prefHeightProperty().bind(mTPpane.heightProperty());
    return loader;
  }

  public void createDefaultView() {
    try {
      FXMLLoader loader = createBasicTrustPolicyView("/layouts/default_lang_layout.fxml");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private FXMLLoader createTrustPolicyView(TrustPolicyModel model, String s) throws IOException {
    mActModel = model;
    return createBasicTrustPolicyView(s);
  }

  public void createGraphicalView(TrustPolicyModel model) throws java.io.IOException {
    FXMLLoader loader = createTrustPolicyView(model, "/layouts/graphical_lang_layout.fxml");
    GraphicalLanguageView graphicalView = (GraphicalLanguageView) loader.getController();
    graphicalView.initController((GraphicalLanguageModel) model, this);
    sLanguageView = graphicalView;
  }

  public void createNatualView(TrustPolicyModel model) throws IOException {
    FXMLLoader loader = createTrustPolicyView(model, "/layouts/natural_lang_layout.fxml");
    NaturalLanguageView naturalView = (NaturalLanguageView) loader.getController();
    naturalView.initController((NaturalLanguageModel) model, this);
    sLanguageView = naturalView;
  }

  public void createScriptView(TrustPolicyModel model) throws IOException {
    FXMLLoader loader = createTrustPolicyView(model, "/layouts/script_lang_layout.fxml");
    ScriptLanguageView scriptView = (ScriptLanguageView) loader.getController();
    scriptView.initController((ScriptLanguageModel) model, this);
    sLanguageView = scriptView;
  }

  public void openDialogs() {
    TrustPolicyModel.TplType type = openChoseDialog();
    if (type != null) {
      String name = openNameDialog();
      if (name != null) {
        createTrustPolicy(name, type);
      }
    }
  }

  private TrustPolicyModel.TplType openChoseDialog() {
    try {
      FXMLLoader loader = new FXMLLoader();
      Parent root = loader.load(getClass().getResource("/layouts/chose_dialog.fxml").openStream());
      ChooseDialogView view = (ChooseDialogView) loader.getController();
      view.initController(mMainModel);
      CustomStage stage = new CustomStage();
      stage.setTitle("New Trust Policy");
      stage.setScene(new Scene(root, 600, 500));
      root.setStyle("-fx-font-weight: bold");
//      new JMetro(JMetro.Style.LIGHT).applyTheme(root);
      stage.showAndWait();
      return stage.getTPLtype();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String openNameDialog() {
    try {
      FXMLLoader loader = new FXMLLoader();
      Parent root = loader.load(getClass().getResource("/layouts/name_dialog.fxml").openStream());
      NameDialogView view = (NameDialogView) loader.getController();
      view.initController(mMainModel);
      CustomStage stage = new CustomStage();
      stage.setTitle("New Trust Policy");
      stage.setScene(new Scene(root, 500, -1));
      root.setStyle("-fx-font-weight: bold");
//      new JMetro(JMetro.Style.LIGHT).applyTheme(root);
      stage.showAndWait();
      return stage.getName();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public MainModel getMainModel() {
    return mMainModel;
  }

  public TrustPolicyModel getActModel() {
    return mActModel;
  }

  public ILanguageView getLanguageView() {
    return sLanguageView;
  }

  public void delete(TrustPolicyModel item) {
    getMainModel().getTrustPolicyModels().remove(item);
    mMainView.getTpListView().setItems(FXCollections.observableList(getMainModel().getTrustPolicyModels()));
    if (!SaveManager.trash(item.mName))
      logger.debug("Can not delete: "+ item.mName);
  }

  public MainView getMainView() {
    return mMainView;
  }
}
