package eu.lightest.tpat.mvc.model;

import eu.lightest.tpat.mvc.controller.NaturalStatementController;
import eu.lightest.tpat.utils.DragDrop.SnapBlockModel;

import java.util.ArrayList;
import java.util.List;

public class NaturalStatementModel implements java.io.Serializable{

  private final NaturalLanguageModel mNaturalLanguageModel;

  private String mDomain;
  private List<SnapBlockModel> mSnappingBlockList = new ArrayList<>();

  private transient NaturalStatementController mController;
  private double mHeight = 250;

  public NaturalStatementModel(NaturalLanguageModel naturalLanguageCotroller) {
    this.mNaturalLanguageModel = naturalLanguageCotroller;
  }

  public List<SnapBlockModel> getBlockList() {
    return mSnappingBlockList;
  }

  public NaturalStatementController getController() {
    return mController;
  }

  public void setmController(NaturalStatementController mController) {
    this.mController = mController;
  }

  public String getDomain() {
    return mDomain;
  }

  public void setDomain(String domain) {
    this.mDomain = domain;
  }

  public NaturalLanguageModel getNaturalLanguageModel() {
    return mNaturalLanguageModel;
  }

  public double getHeight() {
    return mHeight;
  }

  public void setHeight(Number newValue) {
    mHeight = newValue.doubleValue();
  }
}
