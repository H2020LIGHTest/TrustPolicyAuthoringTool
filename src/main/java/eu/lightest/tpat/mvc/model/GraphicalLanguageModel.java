package eu.lightest.tpat.mvc.model;

import eu.lightest.tpat.utils.DragDrop.GraphBlockModel;

import java.util.ArrayList;
import java.util.List;

public class GraphicalLanguageModel extends TrustPolicyModel{

  private List<GraphBlockModel> mPartOfBlocks = new ArrayList<>();

  private List<GraphBlockModel> mEquivalentBlocks = new ArrayList<>();


  public GraphicalLanguageModel(String name, TplType type){
    super(name, type);
  }

  public List<GraphBlockModel> getListPartOf() {
    return mPartOfBlocks;
  }

  public List<GraphBlockModel> getListEquivalent() {
    return mEquivalentBlocks;
  }
}
