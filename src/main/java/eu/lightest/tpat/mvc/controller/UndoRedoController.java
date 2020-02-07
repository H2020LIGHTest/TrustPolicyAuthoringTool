package eu.lightest.tpat.mvc.controller;


import eu.lightest.tpat.mvc.model.NaturalLanguageModel;
import eu.lightest.tpat.mvc.model.TrustPolicyModel;

import java.io.*;

public abstract class UndoRedoController {

  protected TrustPolicyModel redo(TrustPolicyModel model) {
    if (model.nextModelState != null) {
      return model.nextModelState;
    } else return null;
  }

  protected TrustPolicyModel undo(TrustPolicyModel model) {
    if (model.previousModelState != null) {
      return model.previousModelState;
    } else return null;
  }

  protected void addToHistory(TrustPolicyModel mModel) {
    mModel.nextModelState = null;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);) {
      oos.writeObject(mModel);
      try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
           ObjectInputStream ois = new ObjectInputStream(bais);) {
        TrustPolicyModel copy = (TrustPolicyModel) ois.readObject();
        mModel.addToHistory(copy);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
