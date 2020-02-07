package eu.lightest.tpat.mvc.view;

import eu.lightest.tpat.mvc.controller.ChooseDialogController;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import eu.lightest.tpat.mvc.model.MainModel;


public class ChooseDialogView {
  public ImageView mBtnThreeImgV1;
  public ImageView mBtnTwoImgV1;
  public ImageView mBtnOneImgV1;
  final double mBtnImgSize = 64;
  private ChooseDialogController mController = new ChooseDialogController(this);


  @FXML
  Label mHeader;

  @FXML
  ImageView mBtnOneImgV;

  @FXML
  Label mBtnOneTitel;
  @FXML
  Label mBtnOneText;
  @FXML
  ImageView mBtnTwoImgV;
  @FXML
  Label mBtnTwoTitel;
  @FXML
  Label mBtnTwoText;
  @FXML
  ImageView mBtnThreeImgV;
  @FXML
  Label mBtnThreeTitel;
  @FXML
  Label mBtnThreeText;
  @FXML
  Button mGraphicalBtn;

  @FXML
  Button mNaturalBtn;

  @FXML
  Button mScriptBtn;
  @FXML
  public void initialize() {
    // TODO replace strings with localization functions
    mHeader.setText("In which layer would you like to create the Trust Policy?");

    //btn1
    mBtnOneImgV.setImage(new Image("/images/USTUTT/64x64/GE.png"));
    mBtnOneImgV1.setImage(new Image("/images/USTUTT/64x64/GE_skill.png"));
    mBtnOneImgV.setFitWidth(mBtnImgSize);
    mBtnOneImgV.setFitHeight(mBtnImgSize);
    mBtnOneTitel.setText("Graphical Editor");
    mBtnOneText.setText("In this layer you have the option to work in a more visual and simple way. We recommend this editor for all skill levels.");
    //btn2
    mBtnTwoImgV.setImage(new Image("/images/USTUTT/64x64/NL.png"));
    mBtnTwoImgV1.setImage(new Image("/images/USTUTT/64x64/NL_skill.png"));
    mBtnTwoImgV.setFitWidth(mBtnImgSize);
    mBtnTwoImgV.setFitHeight(mBtnImgSize);
    mBtnTwoTitel.setText("Natural Language");
    mBtnTwoText.setText("In this layer you have the ability to make more complex Turst Policies that include not just binomial policies but also ordinal ones. We recommend this language for intermediate users upwards.");
    //btn3
    mBtnThreeImgV.setImage(new Image("/images/USTUTT/64x64/TPL.png"));
    mBtnThreeImgV1.setImage(new Image("/images/USTUTT/64x64/TPL_skill.png"));
    mBtnThreeImgV.setFitWidth(mBtnImgSize);
    mBtnThreeImgV.setFitHeight(mBtnImgSize);
    mBtnThreeTitel.setText("Trust Policy Language");
    mBtnThreeText.setText("This layer gives you complete freedom to make very complex and detailed Trust Policies. We recommend this language for advanced users who amongst other want to make tuple attribute trust policies.");
  }

  public void initController(MainModel mainModel) {
    mController.init(mainModel);
  }

  @FXML
  public void onBtnOne() {
    mController.doBtnAction(TrustPolicyModel.TplType.graphical);

  }

  @FXML
  public void onBtnTwo() {
    mController.doBtnAction(TrustPolicyModel.TplType.natural);

  }

  @FXML
  public void onBtnThree() {
    mController.doBtnAction(TrustPolicyModel.TplType.script);
  }

  @FXML
  public void onCancel() {
    mController.cancelAction();
  }

  public Label getHeader() {
    return mHeader;
  }
}
